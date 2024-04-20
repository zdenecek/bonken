package cz.matfyz.zdenektomis.bonken.ui;

import cz.matfyz.zdenektomis.bonken.model.Card;

/**
 * Class for accessing image resources.
 */
public class Resources {
    private static final String[] images = new String[]
            {"2c.png", "3c.png", "4c.png", "5c.png", "6c.png", "7c.png", "8c.png", "9c.png", "10c.png", "jc.png", "qc.png", "kc.png", "ac.png",
                    "2d.png", "3d.png", "4d.png", "5d.png", "6d.png", "7d.png", "8d.png", "9d.png", "10d.png", "jd.png", "qd.png", "kd.png", "ad.png",
                    "2h.png", "3h.png", "4h.png", "5h.png", "6h.png", "7h.png", "8h.png", "9h.png", "10h.png", "jh.png", "qh.png", "kh.png", "ah.png",
                    "2s.png", "3s.png", "4s.png", "5s.png", "6s.png", "7s.png", "8s.png", "9s.png", "10s.png", "js.png", "qs.png", "ks.png", "as.png"};

    private static final String path = "/cz/matfyz/zdenektomis/bonken/ui/";

    private Resources() {
    }

    /**
     * Returns the path to the image of the given card.
     *
     * @param card the card to get the image of
     * @return the path to the image of the given card
     */
    public static String getImage(Card card) {
        return path + "cards/" + images[card.suit().ordinal() * 13 + card.value().ordinal()];
    }

    /**
     * Returns the path to the main css file.
     *
     * @return the path to the css file
     */
    public static String getCss() {
        return path + "style.css";
    }

}
