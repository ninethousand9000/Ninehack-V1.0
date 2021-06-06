package me.ninethousand.ninehack.util;

import com.google.gson.*;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.FeatureManager;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class ConfigUtil {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void createDirectory() throws IOException {
        if (!Files.exists(Paths.get("ninehack/"))) {
            Files.createDirectories(Paths.get("ninehack/"));
        }

        if (!Files.exists(Paths.get("ninehack/modules"))) {
            Files.createDirectories(Paths.get("ninehack/modules"));
        }

        if (!Files.exists(Paths.get("ninehack/components"))) {
            Files.createDirectories(Paths.get("ninehack/components"));
        }

        if (!Files.exists(Paths.get("ninehack/gui"))) {
            Files.createDirectories(Paths.get("ninehack/gui"));
        }

        if (!Files.exists(Paths.get("ninehack/social"))) {
            Files.createDirectories(Paths.get("ninehack/social"));
        }
    }

    public static void registerFiles(String name, String path) throws IOException {
        if (Files.exists(Paths.get("ninehack/" + path + "/" + name + ".json"))) {
            File file = new File("ninehack/" + path + "/" + name + ".json");
            file.delete();
        }

        Files.createFile(Paths.get("ninehack/" + path + "/" + name + ".json"));
    }

    public static void saveConfig() {
        try {
            saveModules();
            /*saveComponents();
            saveClickGUI();
            saveHUDEditor();
            saveFriends();
            saveEnemies();*/
            savePrefix();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void loadConfig() {
        try {
            createDirectory();
            loadModules();
            /*loadComponents();
            loadClickGUI();
            loadHUDEditor();
            loadFriends();
            loadEnemies();*/
            loadPrefix();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static void saveModules() throws IOException {
        for (Feature feature : FeatureManager.getFeatures()) {
            registerFiles(feature.getName(), "modules");
            OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("ninehack/modules/" + feature.getName() + ".json"), StandardCharsets.UTF_8);

            JsonObject moduleObject = new JsonObject();
            JsonObject settingObject = new JsonObject();
            JsonObject subSettingObject = new JsonObject();

            moduleObject.add("Name", new JsonPrimitive(feature.getName()));
            moduleObject.add("Enabled", new JsonPrimitive(feature.isEnabled()));
            moduleObject.add("Drawn", new JsonPrimitive(feature.isDrawn()));
            moduleObject.add("Bind", new JsonPrimitive(feature.getKey()));

            for (Setting<?> setting : feature.getSettings()) {
                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingObject.add(booleanSetting.getName(), new JsonPrimitive(booleanSetting.getValue()));

                    if (booleanSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : booleanSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Enum) {
                    Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                    settingObject.add(enumSetting.getName(), new JsonPrimitive(String.valueOf(enumSetting.getValue())));

                    if (enumSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : enumSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    settingObject.add(integerSetting.getName(), new JsonPrimitive(integerSetting.getValue()));

                    if (integerSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : integerSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    settingObject.add(doubleSetting.getName(), new JsonPrimitive(doubleSetting.getValue()));

                    if (doubleSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : doubleSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }

                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    settingObject.add(floatSetting.getName(), new JsonPrimitive(floatSetting.getValue()));

                    if (floatSetting.hasSubSettings()) {
                        for (Setting<?> subSetting : floatSetting.getSubSettings()) {
                            if (subSetting.getValue() instanceof Boolean) {
                                Setting<Boolean> subBooleanSetting = (Setting<Boolean>) subSetting;
                                subSettingObject.add(subBooleanSetting.getName(), new JsonPrimitive(subBooleanSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Enum) {
                                Setting<Enum<?>> subEnumSetting = (Setting<Enum<?>>) subSetting;
                                subSettingObject.add(subEnumSetting.getName(), new JsonPrimitive(String.valueOf(subEnumSetting.getValue())));
                            }

                            if (subSetting.getValue() instanceof Color) {
                                Setting<Color> subColourSetting = (Setting<Color>) subSetting;

                                JsonObject subColourObject = new JsonObject();

                                subColourObject.add("Red", new JsonPrimitive(subColourSetting.getValue().getRed()));
                                subColourObject.add("Green", new JsonPrimitive(subColourSetting.getValue().getGreen()));
                                subColourObject.add("Blue", new JsonPrimitive(subColourSetting.getValue().getBlue()));
                                subColourObject.add("Alpha", new JsonPrimitive(subColourSetting.getValue().getAlpha()));

                                subSettingObject.add(subColourSetting.getName(), subColourObject);
                            }

                            if (subSetting.getValue() instanceof Integer) {
                                NumberSetting<Integer> subIntegerSetting = (NumberSetting<Integer>) subSetting;
                                subSettingObject.add(subIntegerSetting.getName(), new JsonPrimitive(subIntegerSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Double) {
                                NumberSetting<Double> subDoubleSetting = (NumberSetting<Double>) subSetting;
                                subSettingObject.add(subDoubleSetting.getName(), new JsonPrimitive(subDoubleSetting.getValue()));
                            }

                            if (subSetting.getValue() instanceof Float) {
                                NumberSetting<Float> subFloatSetting = (NumberSetting<Float>) subSetting;
                                subSettingObject.add(subFloatSetting.getName(), new JsonPrimitive(subFloatSetting.getValue()));
                            }
                        }
                    }
                }
            }

            moduleObject.add("Settings", settingObject);
            settingObject.add("SubSettings", subSettingObject);

            String jsonString = gson.toJson(new JsonParser().parse(moduleObject.toString()));

            fileOutputStreamWriter.write(jsonString);
            fileOutputStreamWriter.close();
        }
    }

    public static void loadModules() throws IOException {
        for (Feature feature : FeatureManager.getFeatures()) {
            if (!Files.exists(Paths.get("ninehack/modules/" + feature.getName() + ".json"))) continue;

            InputStream inputStream = Files.newInputStream(Paths.get("ninehack/modules/" + feature.getName() + ".json"));
            JsonObject moduleObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

            if (moduleObject.get("Name") == null || moduleObject.get("Enabled") == null || moduleObject.get("Drawn") == null || moduleObject.get("Bind") == null) continue;

            JsonObject settingObject = moduleObject.get("Settings").getAsJsonObject();
            JsonObject subSettingObject = settingObject.get("SubSettings").getAsJsonObject();

            for (Setting<?> setting : feature.getSettings()) {
                JsonElement settingValueObject = null;

                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingValueObject = settingObject.get(booleanSetting.getName());

                }

                if (setting.getValue() instanceof Enum) {
                    Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                    settingValueObject = settingObject.get(enumSetting.getName());


                }

                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    settingValueObject = settingObject.get(integerSetting.getName());

                }

                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    settingValueObject = settingObject.get(doubleSetting.getName());
                }

                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    settingValueObject = settingObject.get(floatSetting.getName());
                }

                if (settingValueObject != null) {
                    if (setting.getValue() instanceof Boolean) {
                        Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                        booleanSetting.setValue(settingValueObject.getAsBoolean());
                    }

                    if (setting.getValue() instanceof Enum) {
                        Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) setting;
                        EnumUtil.setEnumValue(enumSetting, settingValueObject.getAsString());
                    }

                    if (setting.getValue() instanceof Integer) {
                        NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                        integerSetting.setValue(settingValueObject.getAsInt());
                    }

                    if (setting.getValue() instanceof Double) {
                        NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                        doubleSetting.setValue(settingValueObject.getAsDouble());
                    }

                    if (setting.getValue() instanceof Float) {
                        NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                        floatSetting.setValue(settingValueObject.getAsFloat());
                    }
                }
            }

            feature.setEnabled(moduleObject.get("Enabled").getAsBoolean());
            feature.setDrawn(moduleObject.get("Drawn").getAsBoolean());
            feature.setKey((moduleObject.get("Bind").getAsInt()));
        }
    }

    /*public static void saveClickGUI() throws IOException {
        registerFiles("GUI", "gui");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("sodium/gui/GUI.json"), StandardCharsets.UTF_8);

        JsonObject guiObject = new JsonObject();
        JsonObject windowObject = new JsonObject();

        for (ClickGUIWindow clickGUIWindow : ClickGUIWindow.windows) {
            JsonObject positionObject = new JsonObject();

            positionObject.add("X", new JsonPrimitive(clickGUIWindow.x));
            positionObject.add("Y", new JsonPrimitive(clickGUIWindow.y));
            positionObject.add("Open", new JsonPrimitive(clickGUIWindow.open));

            windowObject.add(clickGUIWindow.category.toString(), positionObject);
        }

        guiObject.add("Windows", windowObject);

        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadClickGUI() throws IOException {
        if (!Files.exists(Paths.get("sodium/gui/ClickGUI.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("sodium/gui/ClickGUI.json"));
        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (guiObject.get("Windows") == null) return;

        JsonObject windowObject = guiObject.get("Windows").getAsJsonObject();

        for (ClickGUIWindow clickGUIWindow : ClickGUIWindow.windows) {
            if (windowObject.get(clickGUIWindow.category.toString()) == null) return;

            JsonObject categoryObject = windowObject.get(clickGUIWindow.category.toString()).getAsJsonObject();

            JsonElement windowXObject = categoryObject.get("X");

            if (windowXObject != null && windowXObject.isJsonPrimitive()) {
                clickGUIWindow.setX(windowXObject.getAsInt());
            }

            JsonElement windowYObject = categoryObject.get("Y");

            if (windowYObject != null && windowYObject.isJsonPrimitive()) {
                clickGUIWindow.setY(windowYObject.getAsInt());
            }

            JsonElement windowOpenObject = categoryObject.get("Open");

            if (windowOpenObject != null && windowOpenObject.isJsonPrimitive()) {
                clickGUIWindow.setOpen(windowOpenObject.getAsBoolean());
            }
        }

        inputStream.close();
    }

    public static void saveHUDEditor() throws IOException {
        registerFiles("HUDEditor", "gui");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("sodium/gui/HUDEditor.json"), StandardCharsets.UTF_8);

        JsonObject guiObject = new JsonObject();
        JsonObject windowObject = new JsonObject();

        for (HUDEditorWindow hudEditorWindow : HUDEditorWindow.windows) {
            JsonObject positionObject = new JsonObject();

            positionObject.add("X", new JsonPrimitive(hudEditorWindow.x));
            positionObject.add("Y", new JsonPrimitive(hudEditorWindow.y));
            positionObject.add("Open", new JsonPrimitive(hudEditorWindow.open));

            windowObject.add("Sodium HUD", positionObject);
        }

        guiObject.add("Windows", windowObject);

        String jsonString = gson.toJson(new JsonParser().parse(guiObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadHUDEditor() throws IOException {
        if (!Files.exists(Paths.get("sodium/gui/HUDEditor.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("sodium/gui/HUDEditor.json"));
        JsonObject guiObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (guiObject.get("Windows") == null) return;

        JsonObject windowObject = guiObject.get("Windows").getAsJsonObject();

        for (HUDEditorWindow hudEditorWindow : HUDEditorWindow.windows) {
            if (windowObject.get("Sodium HUD") == null) return;

            JsonObject categoryObject = windowObject.get("Sodium HUD").getAsJsonObject();

            JsonElement windowXObject = categoryObject.get("X");

            if (windowXObject != null && windowXObject.isJsonPrimitive()) {
                hudEditorWindow.setX(windowXObject.getAsInt());
            }

            JsonElement windowYObject = categoryObject.get("Y");

            if (hudEditorWindow != null && windowYObject.isJsonPrimitive()) {
                hudEditorWindow.setY(windowYObject.getAsInt());
            }

            JsonElement windowOpenObject = categoryObject.get("Open");

            if (windowOpenObject != null && windowOpenObject.isJsonPrimitive()) {
                hudEditorWindow.setOpen(windowOpenObject.getAsBoolean());
            }
        }

        inputStream.close();
    }*/

   /* public static void saveFriends() throws IOException {
        registerFiles("Friends", "Social");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("sodium/social/Friends.json"), StandardCharsets.UTF_8);

        JsonObject mainObject = new JsonObject();
        JsonArray friendArray = new JsonArray();

        for (Friend friend : FriendManager.getFriends()) {
            friendArray.add(friend.getName());
        }

        mainObject.add("Friends", friendArray);

        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadFriends() throws IOException {
        if (!Files.exists(Paths.get("sodium/social/Friends.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("sodium/social/Friends.json"));
        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (mainObject.get("Friends") == null) return;

        JsonArray friendObject = mainObject.get("Friends").getAsJsonArray();
        friendObject.forEach(object -> FriendManager.addFriend(object.getAsString()));

        inputStream.close();
    }

    public static void saveEnemies() throws IOException {
        registerFiles("Enemies", "social");

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream("sodium/social/Enemies.json"), StandardCharsets.UTF_8);

        JsonObject mainObject = new JsonObject();
        JsonArray enemyArray = new JsonArray();

        for (Enemy enemy : EnemyManager.getEnemies()) {
            enemyArray.add(enemy.getName());
        }

        mainObject.add("Enemies", enemyArray);

        String jsonString = gson.toJson(new JsonParser().parse(mainObject.toString()));
        fileOutputStreamWriter.write(jsonString);

        fileOutputStreamWriter.close();
    }

    public static void loadEnemies() throws IOException {
        if (!Files.exists(Paths.get("sodium/social/Enemies.json"))) return;

        InputStream inputStream = Files.newInputStream(Paths.get("sodium/social/Enemies.json"));
        JsonObject mainObject = new JsonParser().parse(new InputStreamReader(inputStream)).getAsJsonObject();

        if (mainObject.get("Enemies") == null) return;

        JsonArray enemyObject = mainObject.get("Enemies").getAsJsonArray();
        enemyObject.forEach(object -> EnemyManager.addEnemy(object.getAsString()));

        inputStream.close();
    }
*/
    public static void savePrefix() throws IOException {
        File prefixFile = new File("ninehack/Prefix.txt");

        if (!prefixFile.exists()) {
            prefixFile.createNewFile();
        }

        OutputStreamWriter fileOutputStreamWriter = new OutputStreamWriter(new FileOutputStream(prefixFile), StandardCharsets.UTF_8);

        fileOutputStreamWriter.write(NineHack.CHAT_PREFIX);
        fileOutputStreamWriter.close();
    }


    public static void loadPrefix() throws IOException {
        if (!Files.exists(Paths.get("ninehack/Prefix.txt"))) return;

        BufferedReader reader = new BufferedReader(new FileReader("ninehack/Prefix.txt"));

        NineHack.CHAT_PREFIX = reader.readLine();
        reader.close();
    }
}
