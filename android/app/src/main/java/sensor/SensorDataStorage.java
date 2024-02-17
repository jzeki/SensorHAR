package sensor;

import java.util.ArrayList;

public class SensorDataStorage {
    private static SensorDataStorage instance = new SensorDataStorage();
    public ArrayList<SensorsSample> Data = new ArrayList<SensorsSample>();
    private long Time;
    private long TripId;
    private long Tbt;
    private long Id;

    public static SensorDataStorage getInstance()
    {
        if (instance == null)
            instance = new SensorDataStorage();

        return instance;
    }


    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }

    public long getTripId() {
        return TripId;
    }

    public void setTripId(long tripId) {
        TripId = tripId;
    }

    public long getTbt() {
        return Tbt;
    }

    public void setTbt(long tbt) {
        Tbt = tbt;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
