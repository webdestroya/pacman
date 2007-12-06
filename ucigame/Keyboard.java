// Keyboard.java

package ucigame;

import java.awt.event.KeyEvent;

/**
 * Every Ucigame has one object named keyboard; the object's methods reveal
 * the most recently pressed key.
 *
 * @author Dan Frost
 */
public class Keyboard
{
	private Ucigame ucigame;
	public final int LEFT  		= KeyEvent.VK_LEFT;
	public final int RIGHT 		= KeyEvent.VK_RIGHT;
	public final int UP    		= KeyEvent.VK_UP;
	public final int DOWN  		= KeyEvent.VK_DOWN;
	public final int PAGE_UP    = KeyEvent.VK_PAGE_UP;
	public final int PAGE_DOWN  = KeyEvent.VK_PAGE_DOWN;
	public final int SPACE 		= KeyEvent.VK_SPACE;
	public final int PERIOD		= KeyEvent.VK_PERIOD;
	public final int END   		= KeyEvent.VK_END;
	public final int ENTER 		= KeyEvent.VK_ENTER;
	public final int BACKQUOTE	= KeyEvent.VK_BACK_QUOTE;
	public final int BACKSLASH	= KeyEvent.VK_BACK_SLASH;
	public final int CAPSLOCK	= KeyEvent.VK_CAPS_LOCK;
	public final int SEMICOLON	= KeyEvent.VK_SEMICOLON;
	public final int COMMA		= KeyEvent.VK_COMMA;
	public final int CONTROL	= KeyEvent.VK_CONTROL;
	public final int DELETE		= KeyEvent.VK_DELETE;
	public final int SLASH		= KeyEvent.VK_SLASH;
	public final int OPENBRACKET	= KeyEvent.VK_OPEN_BRACKET;
	public final int CLOSEBRACKET	= KeyEvent.VK_CLOSE_BRACKET;
	public final int EQUALS		= KeyEvent.VK_EQUALS;
	public final int INSERT		= KeyEvent.VK_INSERT;
	public final int PAUSE		= KeyEvent.VK_PAUSE;
	public final int HOME		= KeyEvent.VK_HOME;
	public final int BACKSPACE	= KeyEvent.VK_BACK_SPACE;
	public final int NUMLOCK	= KeyEvent.VK_NUM_LOCK;
	public final int TAB		= KeyEvent.VK_TAB;
	public final int SHIFT		= KeyEvent.VK_SHIFT;
	public final int QUOTE		= KeyEvent.VK_QUOTE;
	public final int DASH		= KeyEvent.VK_MINUS;
	public final int A	= KeyEvent.VK_A, B = KeyEvent.VK_B, C = KeyEvent.VK_C,
		  D	= KeyEvent.VK_D, E = KeyEvent.VK_E, F = KeyEvent.VK_F,
		  G	= KeyEvent.VK_G, H = KeyEvent.VK_H, I = KeyEvent.VK_I,
		  J	= KeyEvent.VK_J, K = KeyEvent.VK_K, L = KeyEvent.VK_L,
		  M	= KeyEvent.VK_M, N = KeyEvent.VK_N, O = KeyEvent.VK_O,
		  P	= KeyEvent.VK_P, Q = KeyEvent.VK_Q, R = KeyEvent.VK_R,
		  S	= KeyEvent.VK_S, T = KeyEvent.VK_T, U = KeyEvent.VK_U,
		  V	= KeyEvent.VK_V, W = KeyEvent.VK_W, X = KeyEvent.VK_X,
		  Y	= KeyEvent.VK_Y, Z = KeyEvent.VK_Z, K0 = KeyEvent.VK_0,
		  K1 = KeyEvent.VK_1, K2 = KeyEvent.VK_2, K3 = KeyEvent.VK_3,
		  K4 = KeyEvent.VK_4, K5 = KeyEvent.VK_5, K6 = KeyEvent.VK_6,
		  K7 = KeyEvent.VK_7, K8 = KeyEvent.VK_8, K9 = KeyEvent.VK_9,
		  F1 = KeyEvent.VK_F1, F2 = KeyEvent.VK_F2, F3 = KeyEvent.VK_F3,
		  F4 = KeyEvent.VK_F4, F5 = KeyEvent.VK_F5, F6 = KeyEvent.VK_F6,
		  F7 = KeyEvent.VK_F7, F8 = KeyEvent.VK_F8, F9 = KeyEvent.VK_F9,
		  F10 = KeyEvent.VK_F10, F11 = KeyEvent.VK_F11, F12 = KeyEvent.VK_F12;

	/**
	 * Create new keyboard object.
	 *
	 * @param the ucigame object
	 */
	Keyboard(Ucigame _u)
	{
		ucigame = _u;
	}

	/**
	 * Returns the value of the most recently pressed keyboard key.  Note that the
	 * escape key (Esc) is handled in a special way by Ucigame programs, and thus cannot
	 * be detected using keyboard.key(). If Esc is pressed without the Shift key down,
	 * the program immediately stops. If Esc is pressed with Shift, then the program is
	 * suspended; if can be restarted with another Shift-Esc combination.
	 *
	 * @return the integer representation of the most recently pressed keyboard key
	 */
	public int key() { return ucigame.lastKeyPressed;}

	/**
	 * Returns true if the Shift key was held down when the last key was pressed; false otherwise.
	 *
	 * @return the shift key was pressed
	 */
	public boolean shift() { return ucigame.shiftPressed; }

	/**
	 * Returns true if the Ctrl key was held down when the last key was pressed; false otherwise.
	 *
	 * @return the ctrl key was pressed
	 */
	public boolean ctrl() { return ucigame.ctrlPressed; }

	/**
	 * Returns true if the Alt key was held down when the last key was pressed; false otherwise.
	 *
	 * @return the alt key was pressed
	 */
	public boolean alt() { return ucigame.altPressed; }

	// returns true if any of the keys are down
	public boolean isDown(int... _keys)
	{
		if (_keys.length == 0)
		{
			ucigame.logError("isDown() does not have any keys specified.");
			return false;
		}
		for (int k : _keys)
		{
			if (isDown1(k))
				return true;
		}
		return false;
	}

	private boolean isDown1(int _key)
	{
		String hashkey;

		// check keys that only come in one flavor
		//if (_key == A || _key == B || _key == C || _key == D)
		//{
			hashkey = "X" + _key;
			String indicator = ucigame.keysThatAreDown.get(hashkey);
			return indicator != null;
		//}

	}

	public void typematicOn()  { ucigame.typematicIsOff = false; }
	public void typematicOff() { ucigame.typematicIsOff = true; }
}


