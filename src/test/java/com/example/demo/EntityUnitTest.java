package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import com.example.demo.entities.*;

@Nested
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@TestInstance(Lifecycle.PER_CLASS)
class EntityUnitTest {

    @Autowired
    private TestEntityManager entityManager;

	private Doctor d1;
    private Doctor d2;
	private Patient p1;
    private Patient p2;
    private Room r1;
    private Room r2;
    private Appointment a1;
    private Appointment a2;
    private Appointment a3;
    private Appointment a4;
    private Appointment a5;

    /**
     * Here we are testing that we can successfully retrieve, modify and/or save information to the database.
     * Method 'persistAndFlush' from TestEntitiyManager, stores an entity (such as Doctor, Patient or Room) to the
     * database and ensures that the changes are applied. Then we use the 'find' method to search for the entity
     * based on its ID (or name in the case of 'Room').
     */

    @Test
    void shouldGetDoctorId() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Doctor savedDoctor = entityManager.persistAndFlush(d1);
        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        assertThat(retrievedDoctor.getId()).isEqualTo(d1.getId());
    }

    @Test
    void shouldSetDoctorId() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        Doctor savedDoctor = entityManager.persistAndFlush(d1);
        Doctor retrievedDoctor = entityManager.find(Doctor.class, savedDoctor.getId());
        long previousId = retrievedDoctor.getId();
        retrievedDoctor.setId(424242);
        assertThat(retrievedDoctor.getId()).isNotEqualTo(previousId);
    }

    @Test
    void shouldGetPatientId() {
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Patient savedPatient = entityManager.persistAndFlush(p1);
        Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());
        assertThat(retrievedPatient.getId()).isEqualTo(p1.getId());
    }

    @Test
    void shouldSetPatientId() {
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        Patient savedPatient = entityManager.persistAndFlush(p1);
        Patient retrievedPatient = entityManager.find(Patient.class, savedPatient.getId());
        long previousId = retrievedPatient.getId();
        retrievedPatient.setId(424242);
        assertThat(retrievedPatient.getId()).isNotEqualTo(previousId);
    }

    @Test
    void shouldGetRoomName() {
        r1 = new Room("Dermatology");
        Room savedRoom = entityManager.persistAndFlush(r1);
        Room retrievedRoom = entityManager.find(Room.class, savedRoom.getRoomName());
        assertThat(retrievedRoom).isEqualTo(r1);
        assertThat(retrievedRoom.getRoomName()).isEqualTo("Dermatology");
    }

    @Test
    void shouldCreateRoom() {
        Room room = new Room();
        assertThat(room).isNotNull();
    }

    @Test
    void testRoomEqualsAndHashCode() {
        r1 = new Room("Dermatology");
        r2 = new Room("Traumatology");
        assertThat(r1).isNotEqualTo(r2);
        assertThat(r1.hashCode()).isNotEqualTo(r2.hashCode());
    }

    @Test
    void shouldGetAppointmentId() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt1 = LocalDateTime.parse("10:30 01/01/2022", formatter);
        LocalDateTime finishesAt1 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);

        Appointment savedAppointment = entityManager.persistAndFlush(a1);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        assertThat(retrievedAppointment.getId()).isEqualTo(a1.getId());
    }

    @Test
    void shouldSetAppointment() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt2 = LocalDateTime.parse("10:34 01/01/2022", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);

        Appointment savedAppointment = entityManager.persistAndFlush(a2);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        long previousId = retrievedAppointment.getId();
        retrievedAppointment.setId(424242);
        assertThat(retrievedAppointment.getId()).isNotEqualTo(previousId);
    }

    @Test
    void shouldSetRoomInAppointment() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt3 = LocalDateTime.parse("10:35 01/01/2022", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("10:55 01/01/2022", formatter);
        a3 = new Appointment(p1, d1, r1, startsAt3, finishesAt3);

        Appointment savedAppointment = entityManager.persistAndFlush(a3);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        retrievedAppointment.setRoom(r2);
        assertThat(retrievedAppointment.getRoom().getRoomName()).isEqualTo(r2.getRoomName());
    }

    @Test
    void shouldSetDoctorInAppointment() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt4 = LocalDateTime.parse("10:45 01/01/2022", formatter);
        LocalDateTime finishesAt4 = LocalDateTime.parse("11:15 01/01/2022", formatter);
        a4 = new Appointment(p1, d1, r1, startsAt4, finishesAt4);

        Appointment savedAppointment = entityManager.persistAndFlush(a4);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        d2 = new Doctor ("Miren", "Iniesta", 24, "m.iniesta@hospital.accwe");
        long previousId = retrievedAppointment.getDoctor().getId();
        retrievedAppointment.setDoctor(d2);
        assertThat(retrievedAppointment.getDoctor().getId()).isEqualTo(d2.getId());
        assertThat(retrievedAppointment.getDoctor().getId()).isNotEqualTo(previousId);
    }

    @Test
    void shouldSetPatientInAppointment() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt5 = LocalDateTime.parse("11:30 01/01/2022", formatter);
        LocalDateTime finishesAt5 = LocalDateTime.parse("11:45 01/01/2022", formatter);
        a5 = new Appointment(p1, d1, r1, startsAt5, finishesAt5);

        Appointment savedAppointment = entityManager.persistAndFlush(a5);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        p2 = new Patient("Paulino", "Antunez", 37, "p.antunez@email.com");
        long previousId = retrievedAppointment.getPatient().getId();
        retrievedAppointment.setPatient(p2);
        assertThat(retrievedAppointment.getPatient().getId()).isEqualTo(p2.getId());
        assertThat(retrievedAppointment.getPatient().getId()).isNotEqualTo(previousId);
    }

    @Test
    void shouldSetStartsAtInAppointment() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt1 = LocalDateTime.parse("10:30 01/01/2022", formatter);
        LocalDateTime finishesAt1 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);
        LocalDateTime startsAt2 = LocalDateTime.parse("10:34 01/01/2022", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);

        Appointment savedAppointment = entityManager.persistAndFlush(a1);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        LocalDateTime previousStartTime = retrievedAppointment.getStartsAt();
        retrievedAppointment.setStartsAt(a2.getStartsAt());
        assertThat(retrievedAppointment.getStartsAt()).isNotEqualTo(previousStartTime);
    }

    @Test
    void shouldSetFinishesAtInAppointment() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt3 = LocalDateTime.parse("10:35 01/01/2022", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("10:55 01/01/2022", formatter);
        a3 = new Appointment(p1, d1, r1, startsAt3, finishesAt3);
        LocalDateTime startsAt4 = LocalDateTime.parse("10:45 01/01/2022", formatter);
        LocalDateTime finishesAt4 = LocalDateTime.parse("11:15 01/01/2022", formatter);
        a4 = new Appointment(p1, d1, r1, startsAt4, finishesAt4);

        Appointment savedAppointment = entityManager.persistAndFlush(a3);
        Appointment retrievedAppointment = entityManager.find(Appointment.class, savedAppointment.getId());
        LocalDateTime previousFinishTime = retrievedAppointment.getFinishesAt();
        retrievedAppointment.setFinishesAt(a4.getFinishesAt());
        assertThat(retrievedAppointment.getFinishesAt()).isNotEqualTo(previousFinishTime);
    }

    @Test
    void appointmentShouldNotOverlap() {

        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt5 = LocalDateTime.parse("11:30 01/01/2022", formatter);
        LocalDateTime finishesAt5 = LocalDateTime.parse("11:45 01/01/2022", formatter);
        a5 = new Appointment(p1, d1, r1, startsAt5, finishesAt5);
        LocalDateTime startsAt4 = LocalDateTime.parse("10:45 01/01/2022", formatter);
        LocalDateTime finishesAt4 = LocalDateTime.parse("11:15 01/01/2022", formatter);
        a4 = new Appointment(p1, d1, r1, startsAt4, finishesAt4);

        entityManager.persistAndFlush(a4);
        boolean overlaps = a4.overlaps(a5);
        assertThat(overlaps).isFalse();
    }

    @Test // Testing cases 1-2 for Appointment 'overlaps' method
    void appointmentShouldOverlap() {

        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt1 = LocalDateTime.parse("10:30 01/01/2022", formatter);
        LocalDateTime finishesAt1 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);
        LocalDateTime startsAt2 = LocalDateTime.parse("10:34 01/01/2022", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);

        entityManager.persistAndFlush(a1);
        boolean overlaps = a1.overlaps(a2);
        assertThat(overlaps).isTrue();
    }

    @Test // Testing case 3 for Appointment 'overlaps' method
    void appointmentShouldOverlap2() {

        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt3 = LocalDateTime.parse("10:35 01/01/2022", formatter);
        LocalDateTime finishesAt3 = LocalDateTime.parse("10:55 01/01/2022", formatter);
        a3 = new Appointment(p1, d1, r1, startsAt3, finishesAt3);
        LocalDateTime startsAt1 = LocalDateTime.parse("10:30 01/01/2022", formatter);
        LocalDateTime finishesAt1 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a1 = new Appointment(p1, d1, r1, startsAt1, finishesAt1);

        entityManager.persistAndFlush(a1);
        boolean overlaps = a1.overlaps(a3);
        assertThat(overlaps).isTrue();
    }

    @Test // Testing case 4 for Appointment 'overlaps' method
    void appointmentShouldOverlap3() {
        d1 = new Doctor ("Perla", "Amalia", 24, "p.amalia@hospital.accwe");
        p1 = new Patient("Jose Luis", "Olaya", 37, "j.olaya@email.com");
        r1 = new Room("Dermatology");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        LocalDateTime startsAt2 = LocalDateTime.parse("10:34 01/01/2022", formatter);
        LocalDateTime finishesAt2 = LocalDateTime.parse("11:00 01/01/2022", formatter);
        a2 = new Appointment(p1, d1, r1, startsAt2, finishesAt2);
        LocalDateTime startsAt4 = LocalDateTime.parse("10:45 01/01/2022", formatter);
        LocalDateTime finishesAt4 = LocalDateTime.parse("11:15 01/01/2022", formatter);
        a4 = new Appointment(p1, d1, r1, startsAt4, finishesAt4);

        entityManager.persistAndFlush(a2);
        boolean overlaps = a2.overlaps(a4);
        assertThat(overlaps).isTrue();
    }

}