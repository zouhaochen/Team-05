package model.gameelements.order;

import model.GameData;
import model.gameelements.Country;
import model.gameelements.Player;

/**
 * The class to create an object of subclass of Type Order based on Factory design pattern.
 */
public class OrderFactory {

    private static GameData D_GameData;

    public static void setGameData(GameData p_GameData) {
        D_GameData = p_GameData;
    }

    /**
     * Create an object of type Order.
     *
     * @param p_Command the command from console
     * @param p_Player  the object of type Player
     * @return the order
     */
    public static Order CreateOrder(String[] p_Command, Player p_Player) {
        String l_Type = p_Command[0].toLowerCase();
        Order l_Order;
        switch (l_Type) {
            case "deploy":
                l_Order = new DeployOrder();
                l_Order.setOrderInfo(generateDeployOrderInfo(p_Command, p_Player));
                break;
            default:
                System.out.println("\nFail to create an order due to invalid arguments");
                l_Order = null;
        }
        return l_Order;
    }

    /**
     * Generates an object of OrderInfo for DeployOrder
     *
     * @param p_Command the command from console
     * @param p_Player  the object of type Player
     * @return an object of type OrderInfo
     */
    private static OrderInfo generateDeployOrderInfo(String[] p_Command, Player p_Player) {
        String l_CountryID = p_Command[1];
        int l_NumberOfArmy = Integer.parseInt(p_Command[2]);

        OrderInfo l_OrderInfo = new OrderInfo();
        l_OrderInfo.setPlayer(p_Player);
        l_OrderInfo.setDestination(findCountryByName(l_CountryID));
        l_OrderInfo.setNumberOfArmy(l_NumberOfArmy);

        return l_OrderInfo;
    }

    private static Country findCountryByName(String p_CountryId) {
        for (Country l_Country : D_GameData.getCountryList()) {
            if (l_Country.getCountryName().equalsIgnoreCase(p_CountryId)) {
                return l_Country;
            }
        }

        return null;
    }
}
