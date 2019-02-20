package org.rivierarobotics.util;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AbstractPIDSource implements PIDSource {
    private final DoubleSupplier source;
    private PIDSourceType sourceType = PIDSourceType.kDisplacement;

    public AbstractPIDSource(DoubleSupplier source) {
        this.source = source;
    }

    @Override
    public void setPIDSourceType(PIDSourceType pidSource) {
        this.sourceType = pidSource;
    }

    @Override
    public PIDSourceType getPIDSourceType() {
        return sourceType;
    }

    @Override
    public double pidGet() {
        return source.getAsDouble();
    }

}
