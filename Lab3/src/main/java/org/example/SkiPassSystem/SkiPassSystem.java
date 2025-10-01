package org.example.SkiPassSystem;

import org.example.SkiPass.SkiPass;
import org.example.SkiPass.SkiPassClimbUpsLimitation;
import org.example.SkiPass.SkiPassDaysLimitation;
import org.example.SkiPass.enums.SkiPassType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SkiPassSystem {
    private final List<SkiPass> skiPasses = new ArrayList<>();

    public SkiPass Create(SkiPassType cardType, Integer climbUps) {
        try {
            SkiPass skiPass = new SkiPassClimbUpsLimitation(cardType, climbUps);
            skiPasses.add(skiPass);
            return skiPass;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public SkiPass Create(SkiPassType cardType, LocalDateTime startDate, long duration) {
        try {
            SkiPass skiPass = new SkiPassDaysLimitation(cardType, startDate, duration);
            skiPasses.add(skiPass);
            return skiPass;
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void blockSkiPass(Long SkiPassId) {
        for(SkiPass skiPass: skiPasses) {
            if(skiPass.getId().equals(SkiPassId)) {
                skiPass.blockCard();
                break;
            }
        }
    }

    public void activateSkiPass(Long SkiPassId) {
        for(SkiPass skiPass: skiPasses) {
            if(skiPass.getId().equals(SkiPassId)) {
                skiPass.activateCard();
                break;
            }
        }
    }

    public List<SkiPass> getSkiPasses() {
        return skiPasses;
    }
}
