    import java.util.Scanner;
    /**
     * @author Ivan Vasilev
     * The main function that implement the SmartHome system
     * @version 23
     */
    public class SmartHomeManagementSystem {
        /**
         *Check is value integer or not
         * @param string the string value of the integer
         * @return true if the value is integer. Otherwise, return false
         */
        static boolean isInteger(String string) {
            try {
                int number = Integer.parseInt(string);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        /**{@value} the number of the devices*/
        static final int NUMBER_OF_DEVICES = 10;
        /**{@value} of the elements of the command "SetAngle", "SetTemperature", "SetBrightness", "SetColor"*/
        static final int THREE_ELEMENTS_OF_COMMAND = 3;
        /**{@value} is the minimum index of the light*/
        static final int MIN_LIGHT_ID = 0;
        /**{@value} is the maximum index of the light*/
        static final int MAX_LIGHT_ID = 3;
        /**{@value} is the minimum index of the camera*/
        static final int MIN_CAMERA_ID = 4;
        /**{@value} is the maximum index of the camera*/
        static final int MAX_CAMERA_ID = 5;
        /**{@value} is the minimum index of the heater*/
        static final int MIN_HEATER_ID = 6;
        /**{@value} is the maximum index of the heater*/
        static final int MAX_HEATER_ID = 9;
        /**
         * Method checks the errors for Camera smart device and implements the functionality of the camera
         * @param numberOfDevice device ID for Camera
         * @param command the name of the command for Camera device
         * @param smartDevices the array that contains data about all smart devices including Cameras
         * @param splitCommand the split string that contains the information about command, name of Device and its ID
         */
         static void checkerCamera(int numberOfDevice, String command, SmartDevice[] smartDevices, String[] splitCommand) {
             if (numberOfDevice != MIN_CAMERA_ID && numberOfDevice != MAX_CAMERA_ID) { // check, if the deviceID is correct
                System.out.println("The smart device was not found");
                return;
             }
             Camera camera = (Camera) smartDevices[numberOfDevice]; // cast to class Camera to get access Camera's methods
             if (command.equals("SetTemperature")) { // check on not appropriate commands for Camera
                System.out.printf("Camera %d is not a heater\n", numberOfDevice);
                return;
             }
             if (command.equals("TurnOn")) { // turn on the camera
                 if (camera.isOn()) { // check, if camera is already on
                     System.out.printf("Camera %d is already on\n", camera.getDeviceId());
                     return;
                 }
                 System.out.printf("Camera %d is on\n", camera.getDeviceId());
                 camera.setStatus(Status.ON); // set the status ON for camera
             }
             if (command.equals("SetColor")) { // check on not appropriate commands for Camera
                System.out.printf("Camera %d is not a light\n", camera.getDeviceId());
                return;
             }
             if (command.equals("SetBrightness")) {
                 System.out.printf("Camera %d is not a light\n", camera.getDeviceId());
    
             } else if (command.equals("TurnOff")) { // turnOff the Camera
                 if (!camera.isOn()) { // check, if camera is already off
                    System.out.printf("Camera %d is already off\n", camera.getDeviceId());
                    return;
                 }
                 System.out.printf("Camera %d is off\n", camera.getDeviceId());
                 camera.setStatus(Status.OFF); // set the status OFF for camera
                 camera.stopRecording(); // also after off the camera, the recording process is turn off too
    
    
             } else if (command.equals("StartCharging")) { // the camera started charging
                if (camera.isCharging()) { // check, if the camera is already charging
                    System.out.printf("Camera %d is already charging\n", camera.getDeviceId());
                    return;
                }
                System.out.printf("Camera %d is charging\n", camera.getDeviceId());
                camera.startCharging(); // the camera start charging
             } else if (command.equals("StopCharging")) { // camera stop charging
                if (!camera.isCharging()) { // check, if camera is not charging
                    System.out.printf("Camera %d is not charging\n", camera.getDeviceId());
                    return;
                }
                System.out.printf("Camera %d stopped charging\n", camera.getDeviceId());
                camera.stopCharging(); // camera stop charging
             } else if (command.equals("SetAngle")) { // set the angle for the camera
                if (!camera.isOn()) { // check if camera is on
                    System.out.printf("You can't change the status of the Camera %d while it is off\n",
                            camera.getDeviceId());
                    return;
                }
                // implement the string value of angle to integer
                int commandAngle = Integer.parseInt(splitCommand[THREE_ELEMENTS_OF_COMMAND]);
                if (commandAngle > Camera.MAX_CAMERA_ANGLE || commandAngle < Camera.MIN_CAMERA_ANGLE) { // check,
                    // if the angle is in the appropriate range
                    System.out.printf("Camera %d angle should be in the range [-60, 60]\n", camera.getDeviceId());
                    return;
                }
                System.out.printf("Camera %d angle is set to %d\n", camera.getDeviceId(), commandAngle);
                camera.setCameraAngle(commandAngle); // set the angle for the camera
    
             } else if (command.equals("StartRecording")) { // command to start recording a camera
                if (!camera.isOn()) { // check if the camera is off
                    System.out.printf("You can't change the status of the Camera %d while it is off\n",
                            camera.getDeviceId());
                    return;
                }
                if (camera.isRecording()) { // check if camera is already recording
                    System.out.printf("Camera %d is already recording\n", camera.getDeviceId());
                    return;
                }
                System.out.printf("Camera %d started recording\n", camera.getDeviceId());
                camera.startRecording(); // call the method to start recording for camera
    
             } else if (command.equals("StopRecording")) { // stop recording the camera
                 if (!camera.isOn()) { // check if the camera is off
                    System.out.printf("You can't change the status of the Camera %d while it is off\n",
                            camera.getDeviceId());
                    return;
                 }
                 if (!camera.isRecording()) { // check is the camera is not recording
                    System.out.printf("Camera %d is not recording\n", camera.getDeviceId());
                    return;
                 }
                 System.out.printf("Camera %d stopped recording\n", camera.getDeviceId());
                 camera.stopRecording(); //call the method that stop recording from the camera
             }
        }
    
        /**
         * The method  implement the functionality of heaters and also check the validity of commands for heaters
         * @param numberOfDevice the device ID of the heater
         * @param command the name of the command for the heater
         * @param smartDevices the array that contains data about all smart devices including Heaters
         * @param splitCommand the split string that contains the information about command, name of Device and its ID
         */
        static void checkerHeater(int numberOfDevice, String command, SmartDevice[] smartDevices, String[] splitCommand) {
            if (numberOfDevice < MIN_HEATER_ID || numberOfDevice > MAX_HEATER_ID) {
                System.out.println("The smart device was not found"); // check the correctness of the Device ID
                // for the Heater
                return;
            }
            Heater heater = (Heater) smartDevices[numberOfDevice]; // cast to class Heater to get access heater's methods
    
            if (command.equals("StartCharging") || command.equals("StopCharging")) { // check on the not appropriate
                // commands for the heater
                System.out.printf("Heater %d is not chargeable\n", heater.getDeviceId());
                return;
            }
            if (command.equals("SetColor")) { //check on the not appropriate commands for the heater
                System.out.printf("Heater %d is not a light\n", heater.getDeviceId());
                return;
            }
            if (command.equals("SetAngle") || command.equals("StartRecording") || command.equals("StopRecording")) { //check
                // on the not appropriate commands for the heater
                System.out.printf("Heater %d is not a camera\n", heater.getDeviceId());
                return;
            }
            if (command.equals("SetBrightness")) { //check on the not appropriate commands for the heater
                System.out.printf("Heater %d is not a light\n", heater.getDeviceId());
                return;
    
            }
            if (command.equals("TurnOn")) { // command that turn on the heater
                if (heater.isOn()) { // check if a heater is already on
                    System.out.printf("Heater %d is already on\n", heater.getDeviceId());
                    return;
    
                }
                System.out.printf("Heater %d is on\n", heater.getDeviceId());
                heater.setStatus(Status.ON); // set the status ON for the heater
            } else if (command.equals("TurnOff")) {
                if (!heater.isOn()) { // check if a heater is already off
                    System.out.printf("Heater %d is already off\n", heater.getDeviceId());
                    return;
                }
                System.out.printf("Heater %d is off\n", heater.getDeviceId());
                heater.setStatus(Status.OFF); // sets the status OFF for a heater
            } else if (command.equals("SetTemperature")) { // command that sets the temperature for the heater
                if (!heater.isOn()) { // if heater is off, it's impossible to set the temperature
                    System.out.printf("You can't change the status of the Heater %d while it is off\n",
                            heater.getDeviceId());
                    return;
                }
                int temperatureCommand = Integer.parseInt(splitCommand[THREE_ELEMENTS_OF_COMMAND]); // set the value for
                // stringCommand to integer value
                if (temperatureCommand < Heater.MIN_HEATER_TEMP || temperatureCommand > Heater.MAX_HEATER_TEMP) { // check
                    // the correctness of range for the temperature
                    System.out.printf("Heater %d temperature should be in the range [15, 30]\n", heater.getDeviceId());
                    return;
                }
                System.out.printf("Heater %d temperature is set to %d\n", heater.getDeviceId(), temperatureCommand);
                heater.setTemperature(temperatureCommand); // set the temperature for the heater
            }
        }
    
        /**
         * The method CheckLight implement the functionality of lights and also check the validity of commands for lights
         * @param numberOfDevice the device ID of the light
         * @param command the name of the command for the light
         * @param smartDevices the array that contains data about all smart devices including Lights
         * @param splitCommand the split string that contains the information about command, name of Device and its ID
         */
        static void checkerLight(int numberOfDevice, String command, SmartDevice[] smartDevices, String[] splitCommand) {
            if (numberOfDevice < MIN_LIGHT_ID || numberOfDevice > MAX_LIGHT_ID) { //check
                //the correctness of the appropriate IDs for the lights
                System.out.println("The smart device was not found");
                return;
            }
            Light light = (Light) smartDevices[numberOfDevice]; //cast to class Heater to get access heater's methods
            if (command.equals("SetTemperature")) { //check on the not appropriate commands for the light
                System.out.printf("Light %d is not a heater\n", numberOfDevice);
                return;
            }
            if (command.equals("SetAngle") || command.equals("StartRecording") || command.equals("StopRecording")) {
                //check on the not appropriate commands for the light
                System.out.printf("Light %d is not a camera\n", numberOfDevice);
                return;
            }
            if (command.equals("TurnOn")) { // command that set the status ON
                if (light.isOn()) { // check if the light is already ON
                    System.out.printf("Light %d is already on\n", light.getDeviceId());
                    return;
                }
                System.out.printf("Light %d is on\n", light.getDeviceId());
                light.setStatus(Status.ON); //set the status ON
            } else if (command.equals("TurnOff")) { // check if the light is already off
                if (!light.isOn()) { // check if the light is already OFF
                    System.out.printf("Light %d is already off\n", light.getDeviceId());
                    return;
                }
                System.out.printf("Light %d is off\n", light.getDeviceId());
                light.setStatus(Status.OFF); // set the status OFF for the light
            } else if (command.equals("StartCharging")) { // command for starting charging
                if (light.isCharging()) { // check is the light charging or not. If it's already charging print the error
                    System.out.printf("Light %d is already charging\n", light.getDeviceId());
                    return;
                }
                System.out.printf("Light %d is charging\n", light.getDeviceId());
                light.startCharging(); // the light is started charging
            } else if (command.equals("StopCharging")) { // if the command StopCharging
                if (!light.isCharging()) { // check if the light is nor charging
                    System.out.printf("Light %d is not charging\n", light.getDeviceId());
                    return;
                }
                System.out.printf("Light %d stopped charging\n", light.getDeviceId());
                light.stopCharging(); // stop charging the light
            } else if (command.equals("SetBrightness")) { // command to set the brightness level
                String brightnessCommand = splitCommand[THREE_ELEMENTS_OF_COMMAND]; // take a value from the splitCommand
                if (!light.isOn()) { // if the light is off, it is prohibited to change the brightness
                    System.out.printf("You can't change the status of the Light %d while it is off\n", light.getDeviceId());
                    return;
                }
                if (brightnessCommand.equals("LOW")) { // set the LOW level
                    light.setBrightnessLevel(BrightnessLevel.LOW);
                    System.out.printf("Light %d brightness level is set to LOW\n", light.getDeviceId());
                } else if (brightnessCommand.equals("MEDIUM")) { // set the MEDIUM level
                    light.setBrightnessLevel(BrightnessLevel.MEDIUM);
                    System.out.printf("Light %d brightness level is set to MEDIUM\n", light.getDeviceId());
    
                } else if (brightnessCommand.equals("HIGH")) { // set the HIGH level
                    light.setBrightnessLevel(BrightnessLevel.HIGH);
                    System.out.printf("Light %d brightness level is set to HIGH\n", light.getDeviceId());
    
                } else { // Otherwise, there are exist only 3 conditions of brightness level, so it is an error
                    System.out.println("The brightness can only be one of \"LOW\", \"MEDIUM\", or \"HIGH\"");
                }
            } else if (command.equals("SetColor")) { // command that sets the color for the light
                String colorCommand = splitCommand[THREE_ELEMENTS_OF_COMMAND]; // take a value from the splitCommand
                if (!light.isOn()) { // if the light is off, it is prohibited to change the color
                    System.out.printf("You can't change the status of the Light %d while it is off\n", light.getDeviceId());
                    return;
                }
                if (colorCommand.equals("YELLOW")) { // set the yellow color
                    light.setLightColor(LightColor.YELLOW);
                    System.out.printf("Light %d color is set to YELLOW\n", light.getDeviceId());
    
                } else if (colorCommand.equals("WHITE")) { // set the white color
                    light.setLightColor(LightColor.WHITE);
                    System.out.printf("Light %d color is set to WHITE\n", light.getDeviceId());
    
                } else {
                    //Otherwise, there are exist only 2 color for the light, so it becomes an error
                    System.out.println("The light color can only be \"YELLOW\" or \"WHITE\"");
                }
            }
    
        }
    
    
        /**
         * The function, that contains the information about all devices in smart home system. Also,
         * it checks for several errors for the commands.
         * @param args
         */
        public static void main(String[] args) {
            Scanner sc = new Scanner(System.in); // implement the input from the user
            SmartDevice[] smartDevices = new SmartDevice[NUMBER_OF_DEVICES]; // the array of devices
            for (int i = 0; i < NUMBER_OF_DEVICES; i++) { // fill the array with information about devices
                if (i < MIN_CAMERA_ID) { // information about lights
                    smartDevices[i] = new Light(Status.ON, false, BrightnessLevel.LOW, LightColor.YELLOW); //light's info
                }
                if (i == MIN_CAMERA_ID || i == MAX_CAMERA_ID) {
                    //camera information
                    smartDevices[i] = new Camera(Status.ON, false, false, Camera.INITIAL_CAMERA_ANGLE);
                }
                if (i > MAX_CAMERA_ID) {
                    smartDevices[i] = new Heater(Status.ON, Heater.INITIAL_HEATER_TEMP); // heater information
                }
            }
    
    
            while (!sc.hasNext("end")) { // the program will end until finding the "end" command
                String stringCommand = sc.nextLine(); // not split command line
                String[] splitCommand = stringCommand.split(" "); // the arrays of command and its information
                String command = splitCommand[0]; //command
                // check the correctness of commands
                if ((!command.equals("TurnOn") || splitCommand.length != 2 + 1)
                        && (!command.equals("TurnOff") || splitCommand.length != 2 + 1)
                        && (!command.equals("StartCharging") || splitCommand.length != 2 + 1)
                        && (!command.equals("StopCharging") || splitCommand.length != 2 + 1)
                        && (!command.equals("SetTemperature") || splitCommand.length != 2 + 2)
                        && (!command.equals("SetBrightness") || splitCommand.length != 2 + 2)
                        && (!command.equals("SetColor") || splitCommand.length != 2 + 2)
                        && (!command.equals("SetAngle") || splitCommand.length != 2 + 2)
                        && (!command.equals("StartRecording") || splitCommand.length != 2 + 1)
                        && (!command.equals("StopRecording") || splitCommand.length != 2 + 1)
                        && (!command.equals("DisplayAllStatus") || splitCommand.length != 1)) {
                    System.out.println("Invalid command");
                    continue; // if it finds the error, moves the next line
                }
                if (command.equals("DisplayAllStatus")) { // Display the whole information about devices at home system
                    for (SmartDevice smartDevice : smartDevices) {
                        System.out.println(smartDevice.displayStatus());
                    }
                    continue; // if it finds the error, moves the next line
                }
                String deviceString = splitCommand[1]; // set the string value of the device ID
                //check if the device exist or not
                if (!deviceString.equals("Camera") && !deviceString.equals("Heater") && !deviceString.equals("Light")) {
                    System.out.println("The smart device was not found");
                    continue; // if it finds the error, moves the next line
                }
                if (isInteger(splitCommand[2])) { // check is the string value of device id can be integer or not
                    int numberOfDevice = Integer.parseInt(splitCommand[2]);
                } else {
                    System.out.println("Invalid command");
                    continue; // if it finds the error, moves the next line
                }
                int numberOfDevice = Integer.parseInt(splitCommand[2]); // set the integer value of device ID
                if (numberOfDevice < 0 || numberOfDevice > smartDevices.length) {
                    System.out.println("The smart device was not found");
                    continue; // if it finds the error, moves the next line
                }
                // check is the value of temperature can be integer or not
                if (command.equals("SetTemperature")) {
                    try {
                        int temperatureCommand = Integer.parseInt(splitCommand[THREE_ELEMENTS_OF_COMMAND]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command");
                        continue; // if it finds the error, moves the next line
                    }
                }
                // check is the value of angle can be integer or not
                if (command.equals("SetAngle")) {
                    try {
                        int angleCommand = Integer.parseInt(splitCommand[THREE_ELEMENTS_OF_COMMAND]);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid command");
                        continue; // if it finds the error, moves the next line
                    }
                }
                if (deviceString.equals("Camera")) { // if the device name is Camera, it's implement the checkCamera method
                    checkerCamera(numberOfDevice, command, smartDevices, splitCommand);
                }
                if (deviceString.equals("Heater")) { //if the device name is Heater, it's implement the checkHeater method
                    checkerHeater(numberOfDevice, command, smartDevices, splitCommand);
                }
                if (deviceString.equals("Light")) { //if the device name is Light, it's implement the checkLight method
                    checkerLight(numberOfDevice, command, smartDevices, splitCommand);
                    }
                }
            }
        }
    
    /**
     * abstract class that contain the general information about devices
     */
    abstract class SmartDevice implements Controllable {
        /** the status of a device*/
        private Status status;
        /** device ID of the device*/
        private int deviceId;
        /** the number of device at home system*/
        private static int numberOfDevices;
    
        /**
         * The constructor of the SmartDevice class
         * @param status status of the device
         */
        public SmartDevice(Status status) {
            this.status = status;
            this.deviceId = numberOfDevices++;
        }
    
    
        /**
         * this function shows the information about the device
         * @return the statuses of device
         */
        public String displayStatus() {
            return status.toString();
        }
    
        /**
         * the function to get access the device ID value
         * @return the device ID of the  device
         */
        public int getDeviceId() {
            return deviceId;
        }
    
        /**
         * function that set ID for the device
         * @param deviceId the device ID
         */
        public void setDeviceId(int deviceId) {
            this.deviceId = deviceId;
        }
    
        /**
         * function that get the status of the device
         * @return status of the device
         */
        public Status getStatus() {
            return status;
        }
    
        /**
         * function that get access to set status for the device
         * @param status status of the device
         */
        public void setStatus(Status status) {
            this.status = status;
        }
    
        /**
         * turn off the device
         * @return false if it OFF
         */
        @Override
        public boolean turnOff() {
            return false;
        }
        /**
         * turn On the device
         * @return true if it ON
         */
        @Override
        public boolean turnOn() {
            return true;
        }
        /**
         * check status of the device
         * @return if it on return true, otherwise it is off
         */
        @Override
        public boolean isOn() {
            return status == Status.ON;
        };
    
        /**
         * check the status access
         * @return true if it has access, otherwise false
         */
        public boolean checkStatusAccess() {
            return status == Status.ON;
        }
    
        }
    
    /**
     * this enumerator contain two conditions of the device ON or OFF
      */
    enum Status {
        OFF,
        ON
    }
    
    /**
     * the interface contains properties of devices
     */
    interface Controllable {
        boolean turnOff();
        boolean turnOn();
        boolean isOn();
    }
    
    /**
     * the class Camera contains methods for the camera including the Chargeable properties
     */
    class Camera extends SmartDevice implements Chargeable {
        /**{@value}maximum value of the angle for camera*/
        static final int MAX_CAMERA_ANGLE = 60;
        /**{@value}maximum value of the angle for camera*/
        static final int MIN_CAMERA_ANGLE = -60;
        /**{@value} initial value of the angle for camera*/
        static final int INITIAL_CAMERA_ANGLE = 45;
        /** charging status of the camera*/
        private boolean charging;
        /**recording status of the camera*/
        private boolean recording;
        /**angle of the camera*/
        private int angle;
        /**
         * Constructor of the Camera class
         * @param status status of the camera
         * @param charging charging status of the camera
         * @param recording recording status of the camera
         * @param angle value of the angle
         */
        public Camera(Status status, boolean charging, boolean recording, int angle) {
            super(status);
            this.charging = charging;
            this.recording = recording;
            this.angle = angle;
        }
    
        /**
         * get the value of the angle of camera
         * @return value of the angle
         */
        public int getAngle() {
            return angle;
        }
        /**
         * set the value of the angle of camera
         * @return true when the value has changed
         */
        public boolean setCameraAngle(int angle) {
            this.angle = angle;
            return true;
        }
        /**
         * start recording the camera
         * @return true when the camera has started recording
         */
        public boolean startRecording() {
            this.recording = true;
            return true;
        }
        /**
         * stop recording the camera
         * @return true when the camera has started recording
         */
        public boolean stopRecording() {
            this.recording = false;
            return false;
        }
    
        /**
         * check is the camera recording or not
         * @return the status of recording(true or false)
         */
        public boolean isRecording() {
            return recording;
        }
        /**
         * check is the camera charging or not
         * @return the status of charging(true or false)
         */
        @Override
        public boolean isCharging() {
            return charging;
        }
    
        /**
         * start charging of a camera
         * @return true value when the camera has charged
         */
        @Override
        public boolean startCharging() {
            charging = true;
            return true;
        }
        /**
         * start charging of a camera
         * @return true value when the camera has stopped charging
         */
        @Override
        public boolean stopCharging() {
            charging = false;
            return false;
        }
    
        /**
         * shows the whole statuses of the camera
         * @return string that contains information about camera
         */
        @Override
        public String displayStatus() {
            return String.format("Camera %d is %s, the angle is %d, the charging status is %s, "
                           + "and the recording status is %s.",
                    getDeviceId(),
                    getStatus(), getAngle(), isCharging(), isRecording()
                    );
    
        }
    }
    
    /**
     * this interface contains properties for the chargeable devices
     */
    interface Chargeable {
        boolean isCharging();
        boolean startCharging();
        boolean stopCharging();
    }
    
    /**
     * enumerator contains three levels of the brightness for lights
     */
    enum BrightnessLevel {
        HIGH,
        MEDIUM,
        LOW
    }
    /**
     * enumerator contains two colors for lights
     */
    enum LightColor {
        WHITE,
        YELLOW
    }
    
    /**
     * the class Light contains methods for the camera including the Chargeable properties
     */
    class Light extends SmartDevice implements Chargeable {
        /**charging status of the light*/
        private boolean charging;
        /** the brightness level of a light*/
        private BrightnessLevel brightnessLevel;
        /** color of a light*/
        private LightColor lightColor;
    
        /**
         * constructor of the Light class
         * @param status status of the light
         * @param charging charging status of the light
         * @param brightnessLevel level of brightness of a light
         * @param lightColor color of the light
         */
        public Light(Status status, boolean charging, BrightnessLevel brightnessLevel, LightColor lightColor) {
            super(status);
            this.charging = charging;
            this.brightnessLevel = brightnessLevel;
            this.lightColor = lightColor;
        }
    
        /**
         * get the value of the Light color
         * @return the light of the color
         */
        public LightColor getLightColor() {
            return lightColor;
        }
    
        /**
         * set the color for the light
         * @param lightColor light of the color
         */
        public void setLightColor(LightColor lightColor) {
            this.lightColor = lightColor;
        }
        /**
         * get the value of the brightness level of light
         * @return the brightness of the light
         */
        public BrightnessLevel getBrightnessLevel() {
            return brightnessLevel;
        }
        /**
         * set the value of the brightness level of light
         * @return the brightness of the light
         */
        public void setBrightnessLevel(BrightnessLevel brightnessLevel) {
            this.brightnessLevel = brightnessLevel;
    
        }
        /**
         * check is the light charging or not
         * @return the status of charging(true or false)
         */
        @Override
        public boolean isCharging() {
            return charging;
        }
        /**
         * start charging of a light
         * @return true value when the camera has charged
         */
        @Override
        public boolean startCharging() {
            charging = true;
            return true;
        }
        /**
         * start charging of a camera
         * @return true value when the camera has stopped charging
         */
        @Override
        public boolean stopCharging() {
            charging = false;
            return true;
        }
        /**
         * shows the whole statuses of the light
         * @return string that contains information about light
         */
        @Override
        public String displayStatus() {
            return String.format("Light %d is %s, the color is %s,"
                           + " the charging status is %s, and the"
                            + " brightness level is %s.", getDeviceId(), getStatus(),
                            getLightColor(), isCharging(), getBrightnessLevel()
                    );
        }
    
    }
    
    /**
     * the class Heater contains methods for the heater
     */
    class Heater extends SmartDevice {
        /** value of temperature of the heater */
        private int temperature;
        /**{@value} is maximum of the heater's temperature */
        static final int MAX_HEATER_TEMP = 30;
        /**{@value} is minimum of the heater's temperature */
        static final int MIN_HEATER_TEMP = 15;
        /**{@value} is initial temperature of the heater*/
        static final int INITIAL_HEATER_TEMP = 20;
    
        /**
         * constructor of Heater class
         * @param status status of the heater
         * @param temperature temperature of the heater
         */
        public Heater(Status status, int temperature) {
            super(status);
            this.temperature = temperature;
        }
    
        /**
         * get the value of the temperature
         * @return the temperature of the heater
         */
        public int getTemperature() {
            return temperature;
        }
        /**
         * set the temperature for the heater
         * @param temperature temperature of the heater
         * @return true when the temperature has changed
         */
        public boolean setTemperature(int temperature) {
            this.temperature = temperature; return true;
        }
        /**
         * shows the whole statuses of the heater
         * @return string that contains information about heater
         */
        @Override
        public String displayStatus() {
            return String.format("Heater %d is %s and the temperature is %d.",
                    getDeviceId(), getStatus(), getTemperature());
        }
    }
    
    
    
