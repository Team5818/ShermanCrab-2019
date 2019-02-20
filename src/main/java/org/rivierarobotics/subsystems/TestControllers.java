package org.rivierarobotics.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.SpeedController;

import java.util.ArrayList;
import java.util.List;

public class TestControllers {
    private static final List<SpeedController> CONTROLLERS = new ArrayList<>();

    static {
        for (int i = 1; i <= 11; i++) {
            SpeedController c;
            switch (i) {
                case 1:
                case 4:
                case 7:
                case 10:
                case 11:
                    c = new WPI_TalonSRX(i);
                    break;
                default:
                    c = new CANSparkMax(i, CANSparkMaxLowLevel.MotorType.kBrushless);
                    break;
            }
            CONTROLLERS.add(c);
        }
    }

    public static SpeedController get(int i) {
        return CONTROLLERS.get(i - 1);
    }
}
