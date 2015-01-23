/*****************************************************************************
 * native_crash_handler.h
 *****************************************************************************
 * Copyright © 2010-2013 VLC authors and VideoLAN
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

#ifndef LIBVLCJNI_NATIVE_CRASH_HANDLER_H
#define LIBVLCJNI_NATIVE_CRASH_HANDLER_H

#include <jni.h>

void init_native_crash_handler(JNIEnv *env, jobject j_libVLC_local);
void destroy_native_crash_handler(JNIEnv *env);

#endif // LIBVLCJNI_NATIVE_CRASH_HANDLER_H
