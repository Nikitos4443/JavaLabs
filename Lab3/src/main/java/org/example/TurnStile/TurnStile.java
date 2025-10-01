package org.example.TurnStile;

import org.example.SkiPass.SkiPass;
import org.example.SkiPass.enums.SkiPassType;
import org.example.SkiPass.exceptions.CardArgumentException;
import org.example.SkiPass.exceptions.CardIsExpiredException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurnStile {
    private List<SkiPass> allows = new ArrayList<>();
    private List<SkiPass> disallows = new ArrayList<>();

    public boolean verify(SkiPass skiPass) {
        try {
            skiPass.useCard();
            allows.add(skiPass);
            return true;
        }catch (CardArgumentException | CardIsExpiredException e){
            disallows.add(skiPass);
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Map<String, List<SkiPass>> getAllPassedSkiPasses() {
        Map<String, List<SkiPass>> result = new HashMap<>();
        result.put("allowed", new ArrayList<>(allows));
        result.put("disallowed", new ArrayList<>(disallows));
        return result;
    }

    public Map<SkiPassType, Map<String, List<SkiPass>>> getPassedSkiPassesByType() {
        Map<SkiPassType, Map<String, List<SkiPass>>> result = new HashMap<>();

        for (SkiPass pass : allows) {
            SkiPassType type = pass.getCardType();
            if (!result.containsKey(type)) {
                Map<String, List<SkiPass>> map = new HashMap<>();
                map.put("allowed", new ArrayList<>());
                map.put("disallowed", new ArrayList<>());
                result.put(type, map);
            }
            result.get(type).get("allowed").add(pass);
        }

        for (SkiPass pass : disallows) {
            SkiPassType type = pass.getCardType();
            if (!result.containsKey(type)) {
                Map<String, List<SkiPass>> map = new HashMap<>();
                map.put("allowed", new ArrayList<>());
                map.put("disallowed", new ArrayList<>());
                result.put(type, map);
            }
            result.get(type).get("disallowed").add(pass);
        }

        return result;
    }
}
