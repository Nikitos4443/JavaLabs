package org.example.SkiPass;

import org.example.SkiPass.enums.SkiPassType;
import org.example.SkiPass.exceptions.CardIsExpiredException;

public abstract class SkiPass {
    public static Long idCounter = 0L;
    Long id;
    SkiPassType cardType;
    boolean isCardBlocked = false;

    protected SkiPass(SkiPassType cardType) {
        this.id = idCounter++;
        this.cardType = cardType;
    }

    protected boolean isCardUsable() {
        return !isCardBlocked;
    }

    public void useCard() {
        if(!isCardUsable()) {
            throw new CardIsExpiredException("Card is expired or blocked");
        }
    }

    public void blockCard() {
        isCardBlocked = true;
    }

    public void activateCard() {
        isCardBlocked = false;
    }

    public Long getId() {
        return id;
    }

    public SkiPassType getCardType() {
        return cardType;
    }
}
