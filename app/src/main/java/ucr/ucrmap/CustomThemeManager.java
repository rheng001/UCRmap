package ucr.ucrmap;

import android.content.Context;
import android.graphics.Color;


import com.mapbox.androidsdk.plugins.building.BuildingPlugin;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.constants.Style;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;


public class CustomThemeManager {

    private static final String BUILDING_EXTRUSION_COLOR = "#c4dbed";
    private static final float BUILDING_EXTRUSION_OPACITY = .8f;
    private int selectedTheme;
    private Context context;
    private Icon foodIcon;
    private Icon coffeeIcon;
    private Icon marketIcon;
    private Icon busIcon;
    private Icon libraryIcon;
    private Icon bikeIcon;


    private Icon mockLocationIcon;
    private int navigationLineColor;
    private MapboxMap mapboxMap;
    private MapView mapView;

    CustomThemeManager(int selectedTheme, Context context,
                       MapView mapView, MapboxMap mapboxMap) {
        this.selectedTheme = selectedTheme;
        this.context = context;
        this.mapboxMap = mapboxMap;
        this.mapView = mapView;
    }
    public CustomThemeManager(Context mContext) {
        this.context = mContext;
    }

    public void initializeTheme() {
        switch (selectedTheme) {
            case R.style.AppTheme_Neutral:
                mapboxMap.setStyle(Style.MAPBOX_STREETS);
                navigationLineColor = context.getResources().getColor(R.color.navigationRouteLine_neutral);
                foodIcon = IconFactory.getInstance(context).fromResource(R.drawable.food);
                coffeeIcon = IconFactory.getInstance(context).fromResource(R.drawable.coffee);
                marketIcon = IconFactory.getInstance(context).fromResource(R.drawable.market);
                busIcon = IconFactory.getInstance(context).fromResource(R.drawable.bus);
                libraryIcon = IconFactory.getInstance(context).fromResource(R.drawable.library);
                bikeIcon = IconFactory.getInstance(context).fromResource(R.drawable.bike);

                mockLocationIcon = IconFactory.getInstance(context).fromResource(R.drawable.neutral_orange_user_location);
                showBuildingExtrusions();
                break;
        }
    }

    public void showBuildingExtrusions() {
        // Use the Mapbox building plugin to display and customize the opacity/color of building extrusions
        BuildingPlugin buildingPlugin = new BuildingPlugin(mapView, mapboxMap);
        buildingPlugin.setVisibility(false);
        buildingPlugin.setOpacity(BUILDING_EXTRUSION_OPACITY);
        buildingPlugin.setColor(Color.parseColor(BUILDING_EXTRUSION_COLOR));
    }

    Icon foodIcon() {
        return foodIcon;
    }

    Icon coffeeIcon() {
        return coffeeIcon;
    }

    Icon marketIcon() {
        return marketIcon;
    }

    Icon busIcon() {
        return busIcon;
    }

    Icon libraryIcon() {
        return libraryIcon;
    }

    Icon bikeIcon() {
        return bikeIcon;
    }

    Icon getMockLocationIcon() {
        return mockLocationIcon;
    }

    int getNavigationLineColor() {
        return navigationLineColor;
    }
}

