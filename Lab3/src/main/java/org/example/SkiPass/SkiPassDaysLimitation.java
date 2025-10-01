package org.example.SkiPass;

import org.example.SkiPass.enums.SkiPassType;
import org.example.SkiPass.exceptions.CardArgumentException;
import org.example.SkiPass.exceptions.CardUnpredictableException;
import org.example.shared.Constants;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

public class SkiPassDaysLimitation extends SkiPass {

    LocalDateTime startDate;
    Duration duration;

    private static final List<Long> allowedDurations =
            Arrays.asList(
                    Constants.oneWorkDayInHours/2,
                    Constants.oneWorkDayInHours,
                    Constants.oneWorkDayInHours*2,
                    Constants.oneWorkDayInHours*5,
                    Constants.oneWorkDayInHours*90
            );

    public SkiPassDaysLimitation(SkiPassType cardType, LocalDateTime startDate, long duration) {
        super(cardType);

        if(!allowedDurations.contains(duration)){
            throw new CardArgumentException("Duration can not take this value");
        }

        this.startDate = startDate;
        this.duration = Duration.ofHours(duration);

        validateDates();
    }

    private void validateDates() {
        switch (cardType) {
            case WEEKENDS:
                if(!validateWeekendDates()) {
                    throw new CardArgumentException("Dates are not meet requirements");
                }
                break;
            case WEEKDAYS:
                if(!validateWeekdaysDates()) {
                    throw new CardArgumentException("Dates are not meet requirements");
                }
                break;
            case SEASON:
                if(!validateSeason()) {
                    throw new CardArgumentException("Dates are not meet requirements");
                }
                break;
            default:
                throw new CardUnpredictableException("Invalid card type");
        }
    }

    private boolean validateSeason() {

        if (duration.toHours() != Constants.oneWorkDayInHours*90) {
            throw new CardArgumentException("Season card must be 90 days");
        }

        return true;
    }

    private boolean validateWeekdaysDates() {
        long hours = duration.toHours();

        if(validateHalfDay(hours)) {
            return true;
        }

        return hours == 8 || hours == 16 || hours == 40;
    }

    private boolean validateWeekendDates() {
        long hours = duration.toHours();

        if(validateHalfDay(hours)) {
            return true;
        }

        return hours == 8 || hours == 16;
    }

    private boolean validateHalfDay(long hours) {
        LocalTime startTime = startDate.toLocalTime();
        return hours == 4 &&
                (startTime.equals(LocalTime.of(9, 0)) || startTime.equals(LocalTime.of(13, 0)));
    }

    private long getHoursLeft() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationDate = getExpirationDateTime();

        if (now.isAfter(expirationDate)) {
            return 0;
        }

        if (now.isBefore(startDate)) {
            return duration.toHours();
        }

        Duration remaining = Duration.between(now, expirationDate);
        if(remaining.toHours() <= Constants.oneWorkDayInHours) {
            return remaining.toHours();
        }

        long totalHours = 0;

        while(remaining.toHours() > 0) {
            if(remaining.toHours() <= Constants.oneWorkDayInHours) {
                totalHours += remaining.toHours();
            } else {
                totalHours += 8;
            }
            remaining = remaining.minusDays(1);
        }
        return totalHours;
    }

    private LocalDateTime getExpirationDateTime() {
        LocalDateTime current = startDate;
        LocalDateTime expirationDate;

        if(duration.toHours() <= Constants.oneWorkDayInHours) {
            expirationDate = startDate.plusHours(duration.toHours());
        } else {
            long totalHours = duration.toHours();
            long fullDays = totalHours / Constants.oneWorkDayInHours;
            long remainingHours = totalHours % Constants.oneWorkDayInHours;

            current = current.plusDays(fullDays);
            expirationDate = LocalDateTime.of(current.toLocalDate(), LocalTime.of(9, 0).plusHours((int)remainingHours));
        }
        return expirationDate;
    }

    @Override
    public void useCard() {
        super.useCard();
    }

    @Override
    public boolean isCardUsable() {
        if(!super.isCardUsable()) {
            return false;
        }

        return startDate.plus(duration).isAfter(LocalDateTime.now());
    }

    @Override
    public String toString() {
        return "SkiPassDaysLimitation{" +
                "id=" + getId() +
                ", cardType=" + getCardType() +
                ", hoursLeft=" + getHoursLeft() +
                ", isCardBlocked=" + isCardBlocked +
                '}';
    }
}
