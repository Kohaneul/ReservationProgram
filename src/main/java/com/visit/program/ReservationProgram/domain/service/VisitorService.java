package com.visit.program.ReservationProgram.domain.service;

import com.visit.program.ReservationProgram.domain.dao.*;
import com.visit.program.ReservationProgram.domain.repository.VisitorRepository;
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
public class VisitorService {

    private final VisitorRepository visitorRepository;

    public List<Visitor> findAll(){
        return visitorRepository.findAll();
    }

    public Visitor findOne(Long id) {
        return visitorRepository.findOne(id);
    }

    //정보 저장
    @Transactional
    public Long saveInfo(SaveVisitor saveVisitor) {
        Visitor visitor = getVisitor(saveVisitor);
        visitorRepository.saveInfo(visitor);
        return visitor.getId();
    }

    @Transactional
    public void updateCheckedInfo(Long id){
        visitorRepository.updateCheckedInfo(true,getNowDate(),id);
    }
    private Visitor getVisitor(SaveVisitor visitor) {
        String writeDate = getNowDate();
        return new Visitor(visitor.getLoginId(),visitor.getPassword(),visitor.getEmployee_name(), visitor.getName(), visitor.getPhone_number(), visitor.getCompany(), visitor.getVisit_date1(), visitor.getVisit_date2()
                , visitor.getWithPerson(), visitor.getPurpose(),writeDate,visitor.getRevised_write_date(),0,false);
    }

    @Transactional
    public void deleteInfo(Long id) {
        visitorRepository.deleteInfo(id);
    }


    private String getNowDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy/MM/dd HH:mm"));
    }


    //정보 수정
    @Transactional
    public void updateInfo(UpdateVisitor updateVisitor) {
        visitorRepository.updateInfo(updateVisitor);
    }



}
