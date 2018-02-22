package ucr.ucrmap;

import com.ncapdevi.fragnav.FragNavController;

/**
 * Created by iiro on 7.6.2016.
 */
public class TabMessage {

    private final int INDEX_NAVIGATION = FragNavController.TAB1;
    private final int INDEX_MAP = FragNavController.TAB2;
    private final int INDEX_PLACES = FragNavController.TAB3;
    private final int INDEX_ABOUT = FragNavController.TAB4;

    public static String get(int menuItemId, boolean isReselection) {
        String message = "Reselected ";

        if (isReselection) {

            switch (menuItemId) {
                case R.id.action_nav:
                    message += "INDEX_NAVIGATION";
                    break;
                case R.id.action_map:
                    message += "INDEX_MAP";
                    break;
                case R.id.action_places:
                    message += "INDEX_PLACES";
                    break;
                case R.id.action_profile:
                    message += "INDEX_ABOUT";
                    break;
            }

        }

        return message;
    }
}
