package notify.test.com.mvvmtest.bean;


import java.util.List;

public class ZhihuData {

    /**
     * date : 20190412
     * stories : [{"images":["https://pic2.zhimg.com/v2-a8860107fbd1eae8c9de7dfb2f370e81.jpg"],"type":0,"id":9709979,"ga_prefix":"041222","title":"小事 · 裸辞了"},{"title":"《冰与火之歌》和《权力的游戏》中分别有哪些预言被验证了？","ga_prefix":"041219","images":["https://pic2.zhimg.com/v2-9fb17c2453b6e9634243bc16e63ec86d.jpg"],"multipic":true,"type":0,"id":9710123},{"images":["https://pic4.zhimg.com/v2-dfa6688f32863d782a2631f40dafd497.jpg"],"type":0,"id":9710146,"ga_prefix":"041216","title":"我们为何会对形似「脸」的非动物体产生错觉？"},{"images":["https://pic2.zhimg.com/v2-77ef8274bdd29395b7a2f099e6c26351.jpg"],"type":0,"id":9710148,"ga_prefix":"041209","title":"脆弱的和反脆弱的"},{"images":["https://pic4.zhimg.com/v2-e1fecf65b9cd4709bfc4eb9c789fb5eb.jpg"],"type":0,"id":9710168,"ga_prefix":"041207","title":"「超级真菌」是什么？人类该如何应对？"},{"images":["https://pic2.zhimg.com/v2-90fcbebb2e5df9f90622b9946c594d81.jpg"],"type":0,"id":9710128,"ga_prefix":"041206","title":"瞎扯 · 如何正确地吐槽"}]
     */

    private String date;
    private List<StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public static class StoriesBean {
        /**
         * images : ["https://pic2.zhimg.com/v2-a8860107fbd1eae8c9de7dfb2f370e81.jpg"]
         * type : 0
         * id : 9709979
         * ga_prefix : 041222
         * title : 小事 · 裸辞了
         * multipic : true
         */

        private int type;
        private int id;
        private String ga_prefix;
        private String title;
        private boolean multipic;
        private List<String> images;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }
}
