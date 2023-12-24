package com.app.appointment;
import org.springframework.stereotype.Service;


@Service
public class AppointmentService {

    public boolean creatRequast(AppointmentForm appointmentForm, AppointmentDb appointmentDb) {
       appointmentDb.setApponitmentService(appointmentForm.getService());
       appointmentDb.setApponitmentHour(appointmentForm.getHour());
       appointmentDb.setApponitmentDay(appointmentForm.getDay());
appointmentDb.setAppointmentId(223);

return true;
    }


}
