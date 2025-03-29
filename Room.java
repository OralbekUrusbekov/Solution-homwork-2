public class Room {
    public boolean getDescription() {
        return false;
    }

    public String listItems() {
        return "qwertyuiop";
    }

    public Room getConnectedRoom(String direction) {
        return new Room();
    }

    public Item getItem(String itemName) {
        return new Item();
    }

    public void removeItem(Item item) {
    }
}
