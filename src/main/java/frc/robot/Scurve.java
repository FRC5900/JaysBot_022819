/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Add your docs here.
 */
public class Scurve 
{
  double[] acc_dec_array = 
  {
    0.00240, 0.00640, 0.01200, 0.01920, 0.02800,
    0.03840, 0.05040, 0.06400, 0.07920, 0.09600,
    0.11440, 0.13440, 0.15600, 0.17920, 0.20400,
    0.23040, 0.25840, 0.28800, 0.31920, 0.35200,
    0.38640, 0.42240, 0.46000, 0.49920, 0.54000,
    0.57760, 0.61360, 0.64800, 0.68080, 0.71200,
    0.74160, 0.76960, 0.79600, 0.82080, 0.84400,
    0.86560, 0.88560, 0.90400, 0.92080, 0.93600,
    0.94960, 0.96160, 0.97200, 0.98080, 0.98800,
    0.99360, 0.99760, 1.00000, 1.00000, 1.00000,
    0.99760, 0.99360, 0.98800, 0.98080, 0.97200,
    0.96160, 0.94960, 0.93600, 0.92080, 0.90400,
    0.88560, 0.86560, 0.84400, 0.82080, 0.79600,
    0.76960, 0.74160, 0.71200, 0.68080, 0.64800,
    0.61360, 0.57760, 0.54000, 0.50080, 0.46000,
    0.42240, 0.38640, 0.35200, 0.31920, 0.28800,
    0.25840, 0.23040, 0.20400, 0.17920, 0.15600,
    0.13440, 0.11440, 0.09600, 0.07920, 0.06400,
    0.05040, 0.03840, 0.02800, 0.01920, 0.01200,
    0.00640, 0.00240, 0.00000, 0.00000, 0.00000
  };

  static int scurve_move_state = 0;
  static int acc_dec_index = 0;
  TalonSRX RightMotor;
  VictorSPX LeftMotor;

  public Scurve( TalonSRX rmotor, VictorSPX lmotor )
  {
     RightMotor = rmotor;
     LeftMotor = lmotor;
  }

  public void scurve_move( )
  {
    switch ( scurve_move_state )
    {
      case 0:
        break;

      case 1:  // Accelerate to target speed
       
        break;

      case 2:  // Move at target speed for duration 
        break;

      case 3:  // Decelerate to stop
        break;

      default:
        scurve_move_state = 0;

        break;
    }
  }
    
}
