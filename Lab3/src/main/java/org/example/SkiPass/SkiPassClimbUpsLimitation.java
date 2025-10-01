package org.example.SkiPass;

import org.example.SkiPass.enums.SkiPassType;
import org.example.SkiPass.exceptions.CardArgumentException;
import org.example.SkiPass.exceptions.CardTypeException;

import java.util.Arrays;
import java.util.List;

public class SkiPassClimbUpsLimitation extends SkiPass {

    Integer climbUpsLeft;
    private static final List<Integer> possibleClimbUps = Arrays.asList(10, 20, 50, 100);

    public SkiPassClimbUpsLimitation(SkiPassType cardType, Integer climbUps) {
        super(cardType);

        if(cardType == SkiPassType.SEASON) {
            throw new CardTypeException("Season card type can only have days limitation");
        }

        if(!possibleClimbUps.contains(climbUps)) {
            throw new CardArgumentException("Invalid climb ups limitation");
        }

        climbUpsLeft = climbUps;
    }

    public Integer getClimbUpsLeft() {
        return climbUpsLeft;
    }

    @Override
    public void useCard() {
        super.useCard();

        climbUpsLeft--;
    }

    @Override
    public boolean isCardUsable() {
        if(!super.isCardUsable()) {
            return false;
        }
        return climbUpsLeft >= 0;
    }

    @Override
    public String toString() {
        return "SkiPassClimbUpsLimitation{" +
                "id=" + getId() +
                ", cardType=" + getCardType() +
                ", climbUpsLeft=" + climbUpsLeft +
                ", isCardBlocked=" + isCardBlocked +
                '}';
    }
}
