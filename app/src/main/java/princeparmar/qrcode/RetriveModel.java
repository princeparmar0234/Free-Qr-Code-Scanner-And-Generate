package princeparmar.qrcode;

public class RetriveModel {
    String id;
    String type;
    String date;
    String mainData;
    String imagePath;

    public RetriveModel() {
    }

    public RetriveModel(String id, String type, String date, String mainData, String imagePath) {
        this.id = id;
        this.type = type;
        this.date = date;
        this.mainData = mainData;
        this.imagePath = imagePath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMainData() {
        return mainData;
    }

    public void setMainData(String mainData) {
        this.mainData = mainData;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
