package com.thrblock.game.quarter.comp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.thrblock.cino.util.math.CRand;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ImageService {
    @Value("${quarter.folder:./img}")
    private String folder;

    private BufferedImage[] imgBuffer;

    private int currentIndex = 0;

    @PostConstruct
    void init() {
        this.imgBuffer = Arrays.stream(new File(folder).listFiles())
                .filter(Objects::nonNull).map(convert()).toArray(BufferedImage[]::new);
    }

    private Function<? super File, ? extends BufferedImage> convert() {
        return t -> {
            try {
                return ImageIO.read(t);
            } catch (IOException e) {
                log.error("IOException in convert", e);
                return null;
            }
        };
    }

    public int getColor(float xf, float yf) {
        BufferedImage img = imgBuffer[currentIndex];
        int w = img.getWidth();
        int h = img.getHeight();
        int dx = (int) (w * xf);
        int dh = (int) (h * yf);
        dx = (dx == w) ? (w - 1) : dx;
        dh = (dh == h) ? (h - 1) : dh;
        return img.getRGB(dx, dh);
    }

    public void repeekRandomly() {
        this.currentIndex = CRand.getRandomNum(0, imgBuffer.length - 1);
    }
}
