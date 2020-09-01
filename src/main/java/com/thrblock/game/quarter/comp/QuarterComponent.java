package com.thrblock.game.quarter.comp;

import java.awt.Color;
import java.awt.event.KeyEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLRect;
import com.thrblock.game.quarter.constant.QuarterConstant;

@Component
public class QuarterComponent extends CinoComponent {
    @Autowired
    private QuarterCircleFactory factory;
    @Autowired
    private ImageService imgService;

    @Override
    public void init() throws Exception {
        autoKeyPushPop();
        GLRect rect = shapeFactory.buildGLRect(QuarterConstant.OUTER_W, QuarterConstant.OUTER_H);
        rect.setAllPointColor(Color.GRAY);
        rect.show();
        factory.place(0, 0, QuarterConstant.OUTER_H);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == 'r') {
            factory.clear();
            factory.place(0, 0, QuarterConstant.OUTER_H);
        } else if (e.getKeyChar() == 'c') {
            imgService.repeekRandomly();
        } else if (e.getKeyChar() == 'f') {
            factory.expend();
        }
    }
}
