/*
 * Copyright 2016 Elvis Hew
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiqi.slog.formatter;


import android.text.TextUtils;

import com.weiqi.slog.SLogConstants;

/**
 * String segments wrapped with borders look like:
 * <br>╔════════════════════════════════════════════════════════════════════════════
 * <br>║String segment 1
 * <br>╟────────────────────────────────────────────────────────────────────────────
 * <br>║String segment 2
 * <br>╟────────────────────────────────────────────────────────────────────────────
 * <br>║String segment 3
 * <br>╚════════════════════════════════════════════════════════════════════════════
 */
public class DefaultBorderFormatter {

    private static final char VERTICAL_BORDER_CHAR = '║';

    // Length: 100.
    private static final String TOP_HORIZONTAL_BORDER =
            "╔═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    // Length: 99.
    private static final String DIVIDER_HORIZONTAL_BORDER =
            "╟─────────────────────────────────────────────────" +
                    "──────────────────────────────────────────────────";

    // Length: 100.
    private static final String BOTTOM_HORIZONTAL_BORDER =
            "╚═════════════════════════════════════════════════" +
                    "══════════════════════════════════════════════════";

    public String format(String thread, String stackTrace, String msg) {
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append(TOP_HORIZONTAL_BORDER).append(SLogConstants.LINE_SEPARATOR);

        if (!TextUtils.isEmpty(thread)) {
            msgBuilder.append(appendVerticalBorder(thread));
            msgBuilder.append(SLogConstants.LINE_SEPARATOR).append(DIVIDER_HORIZONTAL_BORDER)
                    .append(SLogConstants.LINE_SEPARATOR);
        }

        if (!TextUtils.isEmpty(stackTrace)) {
            msgBuilder.append(appendVerticalBorder(stackTrace));
            msgBuilder.append(SLogConstants.LINE_SEPARATOR).append(DIVIDER_HORIZONTAL_BORDER)
                    .append(SLogConstants.LINE_SEPARATOR);
        }

        msgBuilder.append(appendVerticalBorder(msg));
        msgBuilder.append(SLogConstants.LINE_SEPARATOR).append(BOTTOM_HORIZONTAL_BORDER);

        return msgBuilder.toString();
    }

    /**
     * Add {@value #VERTICAL_BORDER_CHAR} to each line of msg.
     *
     * @param msg the message to add border
     * @return the message with {@value #VERTICAL_BORDER_CHAR} in the start of each line
     */
    private static String appendVerticalBorder(String msg) {
        StringBuilder borderedMsgBuilder = new StringBuilder(msg.length() + 10);
        String[] lines = msg.split(SLogConstants.LINE_SEPARATOR);
        for (int i = 0, N = lines.length; i < N; i++) {
            if (i != 0) {
                borderedMsgBuilder.append(SLogConstants.LINE_SEPARATOR);
            }
            String line = lines[i];
            borderedMsgBuilder.append(VERTICAL_BORDER_CHAR).append(line);
        }
        return borderedMsgBuilder.toString();
    }
}
