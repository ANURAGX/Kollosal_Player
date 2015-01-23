/*****************************************************************************
 * aout.h
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

#ifndef LIBVLCJNI_AOUT_H
#define LIBVLCJNI_AOUT_H

#include <stdint.h>

#define AOUT_AUDIOTRACK_JAVA 0
#define AOUT_AUDIOTRACK      1
#define AOUT_OPENSLES        2

int aout_open(void **opaque, char *format, unsigned *rate, unsigned *nb_channels);
void aout_play(void *opaque, const void *samples, unsigned count, int64_t pts);
void aout_pause(void *opaque, int64_t pts);
void aout_close(void *opaque);

#endif // LIBVLCJNI_AOUT_H
