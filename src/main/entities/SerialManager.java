package main.entities;

import com.fazecast.jSerialComm.SerialPort;
import main.Handler;

import java.awt.*;

public class SerialManager extends  GameObject {

    SerialPort arduino;

    public SerialManager(Handler handler, double x, double y)
    {
        super(handler, x, y);
        SerialPort[] ports = SerialPort.getCommPorts();

        for(int i = 0; i < ports.length; i++)
        {
            System.out.println(ports[i].getDescriptivePortName());
        }

        try {
            arduino = SerialPort.getCommPorts()[0];
            arduino.setComPortParameters(2000000, 8, 1, 0);
            arduino.openPort();

            while (arduino.bytesAvailable() == 0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            byte[] readBuffer = new byte[arduino.bytesAvailable()];
            int numRead = arduino.readBytes(readBuffer, readBuffer.length);
            System.out.println("Read " + numRead + " bytes." + (new String(readBuffer)));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }

    private SerialPort[] getSerialPorts() { return SerialPort.getCommPorts(); }

    private void setArduinoPort(SerialPort arduino)
    {
        if(arduino.isOpen())
        {
            arduino.closePort();
        }

        this.arduino = arduino;

        arduino.openPort();
    }

    @Override
    public void tick()
    {

    }

    public void tick(String dataToWrite)
    {
        byte[] bufferData = dataToWrite.getBytes();
        arduino.writeBytes(bufferData, bufferData.length);

//        while (arduino.bytesAvailable() == 0) {
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        byte[] readBuffer = new byte[arduino.bytesAvailable()];
//        int numRead = arduino.readBytes(readBuffer, readBuffer.length);
//        System.out.println("Read " + numRead + " bytes." + (new String(readBuffer)));


    }

    @Override
    public void render(Graphics graphics)
    {

    }

}
