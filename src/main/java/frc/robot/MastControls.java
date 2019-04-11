/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


enum Mast_States
{
  wait_for_cmd, 
  at_home,
  down_to_home,
  up_to_Level1,
  down_to_Level1,
  at_Level1,
  up_to_Level2,
  down_to_Level2,
  at_Level2,
  up_to_Level3,
  at_Level3,
};

public class MastControls 
{
  Joystick jstick;
  Mast_States mast_state;

  public MastControls( Joystick stick )
  {
    System.out.println( "MastControls constructor " );
    jstick = stick;
    mast_state = Mast_States.at_home;
    SmartDashboard.putString("Mast", "At Home");
  }

  public void Mast_Controls()
  {
    switch (mast_state)
    {
      case wait_for_cmd:
        if (jstick.getRawButton(3))
        {
          mast_state = Mast_States.up_to_Level1;
          SmartDashboard.putString("Mast", "At Home");
        }
        break;

      case at_home:
        if (jstick.getRawButton(3))
        {
          mast_state = Mast_States.up_to_Level1;
          SmartDashboard.putString("Mast", "Up to Level1");
        }
        break;

      case down_to_home:
        if (jstick.getRawButton(2) == false)
        {
          mast_state = Mast_States.at_home;
          SmartDashboard.putString("Mast", "At Home");
        }
        break;

      case up_to_Level1:
        if (jstick.getRawButton(3) == false)
        {
          mast_state = Mast_States.at_Level1;
          SmartDashboard.putString("Mast", "At Level1");
        }  
        break;

      case down_to_Level1:
        if (jstick.getRawButton(2) == false)
        {
          mast_state = Mast_States.at_Level1;
          SmartDashboard.putString("Mast", "At Level1");
        }  
        break;

      case at_Level1:
        if (jstick.getRawButton(3))
        {
          mast_state = Mast_States.up_to_Level2;
          SmartDashboard.putString("Mast", "Up to Level2");
        }
        else if (jstick.getRawButton(2))
        {
          mast_state = Mast_States.down_to_home;
          SmartDashboard.putString("Mast", "Down to Home");
        }
        break;

      case up_to_Level2:
        if (jstick.getRawButton(3) == false)       
        {
          mast_state = Mast_States.at_Level2;
          SmartDashboard.putString("Mast", "At Level2");
        }
        break;

      case down_to_Level2:
        if (jstick.getRawButton(2) == false)
        {
          mast_state = Mast_States.at_Level2;
          SmartDashboard.putString("Mast", "At Level2");
        }
        break;

      case at_Level2:
        if (jstick.getRawButton(3))
        {
          mast_state = Mast_States.up_to_Level3;
          SmartDashboard.putString("Mast", "Up to Level3");
        }
        else if (jstick.getRawButton(2))
        {
          mast_state = Mast_States.down_to_Level1;
          SmartDashboard.putString("Mast", "Down to Level1");
        }
        break;

      case up_to_Level3:
        if (jstick.getRawButton(3) == false)
        {
          mast_state = Mast_States.at_Level3;
          SmartDashboard.putString("Mast", "At Level3");
        }  
        break;
      
      case at_Level3:
        if (jstick.getRawButton(2))
        {
          mast_state = Mast_States.down_to_Level2;
          SmartDashboard.putString("Mast", "Down to Level2");
        }
        break;
      
      default:
        mast_state = Mast_States.wait_for_cmd;
        break;

    }
  }
}
