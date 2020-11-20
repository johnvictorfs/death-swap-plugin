# Death Swap plugin for Minecraft 1.16.x

Adds the `/death_swap` command to a Bukkit/Spigot/PaperMC Minecraft Servers to swap players in the server (not randomly, in a set sequence) every X minutes.

The swapping has a 10 second countdown in the chat before it happens (currently not configurable)

![image](https://user-images.githubusercontent.com/37747572/99856835-9fa64800-2b68-11eb-9021-eebf3132a5fc.png)

---

### Setup

- Downloaded latest `.jar` from https://github.com/johnvictorfs/death-swap-plugin/releases/latest
- Put the downloaded file into your server's `plugins` folder
- Done! No further configuration needed.

---

### Commands

- `/death_swap [start|stop|now] (time=5)`
    - **Permissions**
        - `death_swap.tasks`
    - `First argument`
        - `start` Start the Death Swap continuous task to swap players every X minutes
        - `stop` Stop ongoing Death Swap continuous task
        - `now` Run Death Swap once, immediately
    - `Second argument` (default = 5)
        - Period on which swaps happen, in minutes, default is 5 minutes

---

## Build from source

- **IntelliJ IDEA**
    - Clone the project
        ```bash
        git clone https://github.com/johnvictorfs/death-swap-plugin.git
        ```

    ![image](https://user-images.githubusercontent.com/37747572/99855805-610f8e00-2b66-11eb-8379-6ae3591e8636.png)

    - Run Maven Build (`DEATH_SWAP BUILD`) with <kbd>Shift</kbd> + <kbd>F10</kbd>
        - *Or:* create an artifact build (type JAR) with no main class, remove the linked Bukkit/PaperMC libraries (since they will be already available in the server) and compile the .jar file.
    - Compiled `.jar` file will be located at `target/DeathSwap-X.Y.Z.jar`

- **Maven**
    - Run Maven Build
        ```bash
        mvn install
        ```
    - Compiled `.jar` file will be located at `target/DeathSwap-X.Y.Z.jar`

