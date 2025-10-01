package org.example;

import org.example.SkiPass.SkiPass;
import org.example.SkiPass.SkiPassClimbUpsLimitation;
import org.example.SkiPass.enums.SkiPassType;
import org.example.SkiPassSystem.SkiPassSystem;
import org.example.TurnStile.TurnStile;
import org.example.shared.Constants;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        SkiPassSystem system = new SkiPassSystem();
        TurnStile ts = new TurnStile();

        var skiPass = system.Create(SkiPassType.WEEKDAYS, 20);
        if(skiPass != null) {
            System.out.println("[21] Ski pass created: ID=" + skiPass.getId() + ", Type=" + skiPass.getCardType());
            boolean isVerified = ts.verify(skiPass);
            System.out.println(isVerified ? "[23] You can pass" : "[23] You can not pass");
            if (skiPass instanceof SkiPassClimbUpsLimitation climbPass) {
                System.out.println("Remaining climb-ups: " + climbPass.getClimbUpsLeft());
            }
        }

        var skiPass2 = system.Create(SkiPassType.SEASON, LocalDateTime.now(), 90 * Constants.oneWorkDayInHours);
        if(skiPass2 != null) {
            System.out.println("\n[31] Ski pass created: ID=" + skiPass2.getId() + ", Type=" + skiPass2.getCardType());
            boolean isVerified = ts.verify(skiPass2);
            System.out.println(isVerified ? "[33] You can pass" : "[33] You can not pass");
        }

        var skiPass3 = system.Create(SkiPassType.WEEKDAYS, LocalDateTime.of(2025, 9, 20, 9, 0), Constants.oneWorkDayInHours/2);
        if(skiPass3 != null) {
            System.out.println("\n[38] Ski pass created: ID=" + skiPass3.getId() + ", Type=" + skiPass3.getCardType());
            boolean isVerified = ts.verify(skiPass3);
            System.out.println(isVerified ? "[40] You can pass" : "[40] You can not pass");
        }

        var skiPass4 = system.Create(SkiPassType.WEEKENDS, 10);
        if(skiPass4 != null) {
            system.blockSkiPass(skiPass4.getId());
            System.out.println("\n[46] Ski pass created and blocked: ID=" + skiPass4.getId() + ", Type=" + skiPass4.getCardType());
            boolean isVerified = ts.verify(skiPass4);
            System.out.println(isVerified ? "[48] You can pass" : "[48] You can not pass");
        }

        List<SkiPass> allSkiPassesInSystem = system.getSkiPasses();
        System.out.println("\n===== All SkiPasses in system =====\n");
        for(SkiPass sp : allSkiPassesInSystem) {
            System.out.println("ID: " + sp.getId() + ", Type: " + sp.getCardType());
        }

        Map<String, List<SkiPass>> turnStileGeneralReport = ts.getAllPassedSkiPasses();
        System.out.println("\n===== All SkiPasses passed through turnStile =====\n");

        System.out.println(">>> Allowed passes:");
        List<SkiPass> allowed = turnStileGeneralReport.get("allowed");
        if (allowed == null || allowed.isEmpty()) {
            System.out.println("  None");
        } else {
            for (SkiPass sp : allowed) {
                System.out.println("  ID: " + sp.getId() + ", Type: " + sp.getCardType());
            }
        }

        System.out.println("\n>>> Disallowed passes:");
        List<SkiPass> disallowed = turnStileGeneralReport.get("disallowed");
        if (disallowed == null || disallowed.isEmpty()) {
            System.out.println("  None");
        } else {
            for (SkiPass sp : disallowed) {
                System.out.println("  ID: " + sp.getId() + ", Type: " + sp.getCardType());
            }
        }

        Map<SkiPassType, Map<String, List<SkiPass>>> reportByType = ts.getPassedSkiPassesByType();
        System.out.println("\n===== SkiPasses passed through turnStile by type =====\n");

        for (SkiPassType type : reportByType.keySet()) {
            System.out.println(">>> Type: " + type);

            Map<String, List<SkiPass>> statusMap = reportByType.get(type);

            allowed = statusMap.get("allowed");
            System.out.println("  Allowed:");
            if (allowed == null || allowed.isEmpty()) {
                System.out.println("    None");
            } else {
                for (SkiPass sp : allowed) {
                    System.out.println("    ID: " + sp.getId());
                }
            }

            disallowed = statusMap.get("disallowed");
            System.out.println("  Disallowed:");
            if (disallowed == null || disallowed.isEmpty()) {
                System.out.println("    None");
            } else {
                for (SkiPass sp : disallowed) {
                    System.out.println("    ID: " + sp.getId());
                }
            }

            System.out.println();
        }

        System.out.println("===============================================");
    }
}
