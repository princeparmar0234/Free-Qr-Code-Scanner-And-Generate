package princeparmar.qrcode;

import android.provider.BaseColumns;

public class Model {

    public Model(){

    }
    public static final class detail implements BaseColumns{

        public static final String TABLE_NAME="alldetail";
        public static final String COL_ID="id";
        public static final String COL_TYPE="type";
        public static final String COL_DATE="date";
        public static final String COL_MAIN_DATA="mainData";
        public static final String COL_IMAGE_PATH="imagePath";


    }
}
