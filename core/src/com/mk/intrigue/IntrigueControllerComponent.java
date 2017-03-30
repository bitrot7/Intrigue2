//IntrigueControllerComponent.java
/*
*	matt keating
*/
package com.mk.intrigue;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.Controller;

public class IntrigueControllerComponent {
	private Controller controls;
	
	public IntrigueControllerComponent(int player_number) {
		
		if (player_number > 6) {
			System.out.println("Failure: too many Xbox Controllers, Max of 6 players on battlefield.");
			return;
		}
		player_number--;
		Array<Controller> t = Controllers.getControllers();
		if(t.size == 0) {
			System.out.println("Failure:  No Controllers Attached");
			return;
		}
		else if(player_number >= t.size) {
			System.out.println("Failure:  No Controllers Attached for player" + player_number + 1);
			return;
		}
		controls = Controllers.getControllers().get(player_number);
	}
	public Controller getController() {
		return controls;
	}
}