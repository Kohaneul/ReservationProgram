package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.Reservation;
import com.visit.program.ReservationProgram.domain.dao.ReservationInfo;
import com.visit.program.ReservationProgram.domain.dao.SaveReservationInfo;
import com.visit.program.ReservationProgram.domain.dto.MyReservationDTO;
import com.visit.program.ReservationProgram.domain.dto.ReservationDTO;
import com.visit.program.ReservationProgram.domain.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public List<Reservation> findAll(){
        return reservationRepository.findAll();
    }

//    public List<Reservation> findAllFromTodayTo7DaysReservations(ReservationDTO reservationDTO){
//        LocalDateTime today = LocalDateTime.now();
//        reservationDTO.setDate1(today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        reservationDTO.setDate2( today.plusDays(7).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//        return reservationRepository.findAllFromTodayTo7DaysReservations(reservationDTO);
//    }
    public List<Reservation> findMyVisitors(MyReservationDTO reservationDTO){
        return reservationRepository.findMyVisitors(reservationDTO);
    }
    @Transactional
    public void updateCheckedInfo(Long id){
        reservationRepository.updateCheckedInfo(true,id);
    }
    @Transactional
    public void saveInfo(SaveReservationInfo saveReservationInfo){
        reservationRepository.saveInfo(saveReservationInfo);
    };
    @Transactional
    public void deleteInfo(Long id){
        reservationRepository.deleteInfo(id);
    };

    public ReservationInfo findInfo(Long id){
        return reservationRepository.findInfoOne(id);
    }

    public Reservation findOne(Long id){
        return reservationRepository.findOne(id);
    }

    public List<Reservation> findAllDTO(ReservationDTO reservationDTO){
        return  reservationRepository.findAllDTO(reservationDTO);
    }
    public List<Reservation> findAllDTO2(ReservationDTO reservationDTO){
        return  reservationRepository.findAllDTO2(reservationDTO);
    }

}
