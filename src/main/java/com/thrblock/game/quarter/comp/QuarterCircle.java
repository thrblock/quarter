package com.thrblock.game.quarter.comp;

import org.springframework.beans.factory.annotation.Autowired;

import com.thrblock.cino.component.CinoComponent;
import com.thrblock.cino.glshape.GLOval;
import com.thrblock.cino.glshape.proxy.NodeAlphaProxy;
import com.thrblock.game.quarter.constant.QuarterConstant;
import com.thrblock.poolable.Poolable;

public class QuarterCircle extends CinoComponent implements Poolable {
    private static final int ACCUR = 32;

    @Autowired
    private ImageService imgService;

    private QuarterCircleFactory factory;

    private GLOval oval;

    private enum St {
        IN, KEEP, OUT;
    }

    private St current = St.IN;
    private float currentW = 50;

    public QuarterCircle(QuarterCircleFactory f) {
        this.factory = f;
    }

    @Override
    public void init() throws Exception {
        this.oval = shapeFactory.buildGLOval(currentW, ACCUR);
        oval.setFill(true);
        oval.setAlpha(0);
        NodeAlphaProxy alph = oval.proxyNodeAlpha();
        auto(() -> {
            if (St.IN.equals(current) && alph.tearIn(0.06f)) {
                this.current = St.KEEP;
            } else if (St.KEEP.equals(current) && oval.isMouseInside() && currentW > 20) {
                fork();
                this.current = St.OUT;
            } else if (St.OUT.equals(current) && alph.tearOut(0.06f)) {
                interrupt();
            }
        });
    }

    private void fork() {
        float offset = currentW / 4;
        factory.place(oval.getCentralX() - offset, oval.getCentralY() - offset, currentW / 2);
        factory.place(oval.getCentralX() - offset, oval.getCentralY() + offset, currentW / 2);
        factory.place(oval.getCentralX() + offset, oval.getCentralY() + offset, currentW / 2);
        factory.place(oval.getCentralX() + offset, oval.getCentralY() - offset, currentW / 2);
    }

    @Override
    public boolean isAvailable() {
        return !oval.isVisible();
    }

    @Override
    public void interrupt() {
        this.deactivited();
        oval.hide();
    }

    public void activeAt(float x, float y, float w) {
        oval.setCentralX(x);
        oval.setCentralY(y);
        oval.resize(w, w, ACCUR);
        currentW = w;
        oval.setAlpha(0f);
        current = St.IN;
        oval.show();

        float refX = (QuarterConstant.OUTER_W / 2 + x) / QuarterConstant.OUTER_W;
        float refY = (QuarterConstant.OUTER_H / 2 - y) / QuarterConstant.OUTER_H;
        oval.setAllPointColor(imgService.getColor(refX, refY));

        activited();
    }

    public void expend() {
        if (currentW > 20 && St.KEEP.equals(current)) {
            fork();
            this.current = St.OUT;
        }
    }
}
