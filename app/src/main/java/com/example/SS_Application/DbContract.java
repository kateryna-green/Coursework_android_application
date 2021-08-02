package com.example.SS_Application;

import android.provider.BaseColumns;

public final class DbContract {

    public DbContract() {
    }

    public static class LanguagesTable implements BaseColumns {

        public static final String TABLE_NAME = "language_categories";
        public static final String COLUMN_NAME = "name";
    }

    public static  class  CardsTable  implements BaseColumns {

        public static final String TABLE_NAME = "cards_text";

        public static final String COLUMN_TEXT_FRONT = "text_front";
        public static final String COLUMN_TEXT_BACK = "text_back";

        public static final String COLUMN_LANGUAGE_ID = "language_id";
    }

    public static  class  QuestionsTable  implements BaseColumns {

        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";

        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";

        public static final String COLUMN_ANSWER_NR = "answer_nr";

        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_LANGUAGE_ID = "language_id";
    }
}
