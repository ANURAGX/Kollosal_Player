/*****************************************************************************
 * aout.c
 *****************************************************************************
 * Copyright © 2011-2012 VLC authors and VideoLAN
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston MA 02110-1301, USA.
 *****************************************************************************/

#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdint.h>

#include <jni.h>

#include <vlc/vlc.h>

#include "aout.h"

#define LOG_TAG "VLC/JNI/aout"
#include "log.h"

// An audio frame will contain FRAME_SIZE samples
#define FRAME_SIZE (4096*2)

typedef struct
{
    jobject j_libVlc;   /// Pointer to the LibVLC Java object
    jmethodID play;     /// Java method to play audio buffers
    jbyteArray buffer;  /// Raw audio data to be played
} aout_sys_t;

#define THREAD_NAME "jni_aout"
extern int jni_attach_thread(JNIEnv **env, const char *thread_name);
extern void jni_detach_thread();

int aout_open(void **opaque, char *format, unsigned *rate, unsigned *nb_channels)
{
    LOGI ("Opening the JNI audio output");

    aout_sys_t *p_sys = calloc (1, sizeof (*p_sys));
    if (!p_sys)
        goto enomem;

    p_sys->j_libVlc = *opaque;       // Keep a reference to our Java object
    *opaque         = (void*) p_sys; // The callback will need aout_sys_t

    LOGI ("Parameters: %u channels, FOURCC '%4.4s',  sample rate: %uHz",
          *nb_channels, format, *rate);

    JNIEnv *p_env;
    if (jni_attach_thread (&p_env, THREAD_NAME) != 0)
    {
        LOGE("Could not attach the display thread to the JVM !");
        goto eattach;
    }

    // Call the init function.
    jclass cls = (*p_env)->GetObjectClass (p_env, p_sys->j_libVlc);
    jmethodID methodIdInitAout = (*p_env)->GetMethodID (p_env, cls,
                                                        "initAout", "(III)V");
    if (!methodIdInitAout)
    {
        LOGE ("Method initAout() could not be found!");
        goto error;
    }

    LOGV ("Number of channels forced to 2, number of samples to %d", FRAME_SIZE);
    *nb_channels = 2;

    int aout_rate = *rate;
    while (1) {
        (*p_env)->CallVoidMethod (p_env, p_sys->j_libVlc, methodIdInitAout,
                                  aout_rate, *nb_channels, FRAME_SIZE);
        if ((*p_env)->ExceptionCheck (p_env) == 0) {
            *rate = aout_rate;
            break;
        }

        if (aout_rate <= 0) {
            LOGE ("initAout failed, invalid sample rate %dHz", aout_rate);
        } else if (aout_rate != 44100) {
            if (aout_rate < 4000) {
                do {
                    aout_rate *= 2;
                } while (aout_rate < 4000);
            } else if (aout_rate > 48000) {
                do {
                    aout_rate /= 2;
                } while (aout_rate > 48000);
            } else {
                aout_rate = 44100;
            }

            LOGE ("initAout failed, try next sample rate %dHz", aout_rate);
            (*p_env)->ExceptionClear (p_env);
            continue;
        }

        LOGE ("Unable to create audio player!");
#ifndef NDEBUG
        (*p_env)->ExceptionDescribe (p_env);
#endif
        (*p_env)->ExceptionClear (p_env);
        goto error;
    }

    /* Create a new byte array to store the audio data. */
    jbyteArray buffer = (*p_env)->NewByteArray (p_env,
                                                   *nb_channels *
                                                   FRAME_SIZE *
                                                   sizeof (uint16_t) /* =2 */);
    if (buffer == NULL)
    {
        LOGE ("Could not allocate the Java byte array to store the audio data!");
        goto error;
    }

    /* Use a global reference to not reallocate memory each time we run
       the play function. */
    p_sys->buffer = (*p_env)->NewGlobalRef (p_env, buffer);
    /* The local reference is no longer useful. */
    (*p_env)->DeleteLocalRef (p_env, buffer);
    if (p_sys->buffer == NULL)
    {
        LOGE ("Could not create the global reference!");
        goto error;
    }

    // Get the play methodId
    p_sys->play = (*p_env)->GetMethodID (p_env, cls, "playAudio", "([BI)V");
    assert (p_sys->play != NULL);
    jni_detach_thread ();
    return 0;

error:
    jni_detach_thread ();
eattach:
    *opaque = NULL;
    free (p_sys);
enomem:
    return -1;
}

/**
 * Play an audio frame
 **/
void aout_play(void *opaque, const void *samples, unsigned count, int64_t pts)
{
    aout_sys_t *p_sys = opaque;
    JNIEnv *p_env;

    /* How ugly: we constantly attach/detach this thread to/from the JVM
     * because it will be killed before aout_close is called.
     * aout_close will actually be called in an different thread!
     */
    jni_attach_thread (&p_env, THREAD_NAME);

    (*p_env)->SetByteArrayRegion (p_env, p_sys->buffer, 0,
                                  2 /*nb_channels*/ * count * sizeof (uint16_t),
                                  (jbyte*) samples);
    if ((*p_env)->ExceptionCheck (p_env))
    {
        // This can happen if for some reason the size of the input buffer
        // is larger than the size of the output buffer
        LOGE ("An exception occurred while calling SetByteArrayRegion");
        (*p_env)->ExceptionDescribe (p_env);
        (*p_env)->ExceptionClear (p_env);
        return;
    }

    (*p_env)->CallVoidMethod (p_env, p_sys->j_libVlc, p_sys->play,
                              p_sys->buffer,
                              2 /*nb_channels*/ * count * sizeof (uint16_t),
                              FRAME_SIZE);
    // FIXME: check for errors

    jni_detach_thread ();
}

void aout_pause(void *opaque, int64_t pts)
{
    LOGI ("Pausing audio output");
    aout_sys_t *p_sys = opaque;
    assert(p_sys);

    JNIEnv *p_env;
    jni_attach_thread (&p_env, THREAD_NAME);

    // Call the pause function.
    jclass cls = (*p_env)->GetObjectClass (p_env, p_sys->j_libVlc);
    jmethodID methodIdPauseAout = (*p_env)->GetMethodID (p_env, cls, "pauseAout", "()V");
    if (!methodIdPauseAout)
        LOGE ("Method pauseAout() could not be found!");
    (*p_env)->CallVoidMethod (p_env, p_sys->j_libVlc, methodIdPauseAout);
    if ((*p_env)->ExceptionCheck (p_env))
    {
        LOGE ("Unable to pause audio player!");
#ifndef NDEBUG
        (*p_env)->ExceptionDescribe (p_env);
#endif
        (*p_env)->ExceptionClear (p_env);
    }

    jni_detach_thread ();
}

void aout_close(void *opaque)
{
    LOGI ("Closing audio output");
    aout_sys_t *p_sys = opaque;
    assert(p_sys);
    assert(p_sys->buffer);

    JNIEnv *p_env;
    jni_attach_thread (&p_env, THREAD_NAME);

    // Call the close function.
    jclass cls = (*p_env)->GetObjectClass (p_env, p_sys->j_libVlc);
    jmethodID methodIdCloseAout = (*p_env)->GetMethodID (p_env, cls, "closeAout", "()V");
    if (!methodIdCloseAout)
        LOGE ("Method closeAout() could not be found!");
    (*p_env)->CallVoidMethod (p_env, p_sys->j_libVlc, methodIdCloseAout);
    if ((*p_env)->ExceptionCheck (p_env))
    {
        LOGE ("Unable to close audio player!");
#ifndef NDEBUG
        (*p_env)->ExceptionDescribe (p_env);
#endif
        (*p_env)->ExceptionClear (p_env);
    }

    (*p_env)->DeleteGlobalRef (p_env, p_sys->buffer);
    (*p_env)->DeleteGlobalRef (p_env, p_sys->j_libVlc);
    jni_detach_thread ();
    free (p_sys);
}

int aout_get_native_sample_rate(void)
{
    JNIEnv *p_env;
    jni_attach_thread (&p_env, THREAD_NAME);
    jclass cls = (*p_env)->FindClass (p_env, "android/media/AudioTrack");
    jmethodID method = (*p_env)->GetStaticMethodID (p_env, cls, "getNativeOutputSampleRate", "(I)I");
    int sample_rate = (*p_env)->CallStaticIntMethod (p_env, cls, method, 3); // AudioManager.STREAM_MUSIC
    jni_detach_thread ();
    return sample_rate;
}
