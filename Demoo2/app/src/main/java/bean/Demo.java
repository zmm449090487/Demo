package bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Demo {
    @Property(nameInDb = "Lid")
    @Id
    private Long id;
    private int img;
    private String title;

    @Override
    public String toString() {
        return "Demo{" +
                "img=" + img +
                ", title='" + title + '\'' +
                '}';
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Demo(int img, String title) {
        this.img = img;
        this.title = title;
    }

    @Generated(hash = 1442542482)
    public Demo(Long id, int img, String title) {
        this.id = id;
        this.img = img;
        this.title = title;
    }

    @Generated(hash = 571290164)
    public Demo() {
    }
}
