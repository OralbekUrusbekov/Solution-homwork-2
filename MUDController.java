import java.util.Scanner;

/**
 * MUDController:
 * A simple controller that reads player input and orchestrates
 * basic commands like look around, move, pick up items,
 * check inventory, show help, etc.
 */

public class MUDController {

    private final Player player;
    private boolean running;

    /**
     * Constructs the controller with a reference to the current player.
     */
    public MUDController(Player player) {
        this.player = player;
        this.running = true;
    }

    /**
     * Main loop method that repeatedly reads input from the user
     * and dispatches commands until the game ends.
     */
    public void runGameLoop() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в MUD! Введите 'help' для просмотра команд.");
        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();
            handleInput(input);
        }
        scanner.close();
    }

    /**
     * Handle a single command input (e.g. 'look', 'move forward', 'pick up sword').
     */
    public void handleInput(String input) {
        String[] parts = input.split(" ", 2);
        String command = parts[0];
        String argument = parts.length > 1 ? parts[1] : "";

        switch (command) {
            case "look":
                lookAround();
                break;
            case "move":
                move(argument);
                break;
            case "pick":
                if (argument.startsWith("up ")) {
                    pickUp(argument.substring(3));
                } else {
                    System.out.println("Неверная команда. Вы имели в виду 'pick up <предмет>'?");
                }
                break;
            case "inventory":
                checkInventory();
                break;
            case "help":
                showHelp();
                break;
            case "quit":
            case "exit":
                System.out.println("Выход из игры. До свидания!");
                running = false;
                break;
            default:
                System.out.println("Неизвестная команда. Введите 'help' для просмотра списка команд.");
                break;
        }
    }

    /**
     * Look around the current room: describe it and show items/NPCs.
     */
    private void lookAround() {
        Room currentRoom = player.getCurrentRoom();
        if (currentRoom != null) {
            System.out.println(currentRoom.getDescription());
            System.out.println("Предметы здесь: " + currentRoom.listItems());
        } else {
            System.out.println("Вы находитесь в неизвестном месте.");
        }
    }

    /**
     * Move the player in a given direction (forward, back, left, right).
     */
    private void move(String direction) {
        if (direction.isEmpty()) {
            System.out.println("Куда двигаться? Используйте: move <направление>");
            return;
        }
        Room nextRoom = player.getCurrentRoom().getConnectedRoom(direction);
        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            System.out.println("Вы переместились " + direction + ".");
            lookAround();
        } else {
            System.out.println("Вы не можете пойти в этом направлении!");
        }
    }

    /**
     * Pick up an item (e.g. "pick up sword").
     */
    private void pickUp(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.getItem(itemName);
        if (item != null) {
            player.addItemToInventory(item);
            currentRoom.removeItem(item);
            System.out.println("Вы подобрали " + itemName + ".");
        } else {
            System.out.println("Здесь нет предмета с названием '" + itemName + "'!");
        }
    }

    /**
     * Check the player's inventory.
     */
    private void checkInventory() {
        System.out.println("У вас в инвентаре:");
        System.out.println(player.listInventory());
    }

    /**
     * Show help commands.
     */
    private void showHelp() {
        System.out.println("Доступные команды:");
        System.out.println("look - Описать текущую комнату");
        System.out.println("move <направление> - Переместиться в указанном направлении (вперед, назад, влево, вправо)");
        System.out.println("pick up <предмет> - Подобрать предмет");
        System.out.println("inventory - Показать ваш инвентарь");
        System.out.println("help - Показать это сообщение");
        System.out.println("quit / exit - Выйти из игры");
    }

    public static void main(String[] args) {
        Player player = new Player();
        MUDController controller = new MUDController(player);
        controller.runGameLoop();
    }
}
