package net.eugenpaul.adventofcode.y2016.day17;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import net.eugenpaul.adventofcode.helper.HexConverter;

@AllArgsConstructor
public class BuildingArea {

    private int xSize;
    private int ySize;

    public List<Doors> getOpenDoors(String passcode, int xPos, int yPos) {
        String hash = doMd5(passcode);

        List<Doors> response = new ArrayList<>();
        if (yPos > 0 && isOpen(hash, 0)) {
            response.add(Doors.UP);
        }

        if (yPos < ySize - 1 && isOpen(hash, 1)) {
            response.add(Doors.DOWN);
        }

        if (xPos > 0 && isOpen(hash, 2)) {
            response.add(Doors.LEFT);
        }

        if (xPos < xSize - 1 && isOpen(hash, 3)) {
            response.add(Doors.RIGHT);
        }

        return response;
    }

    private String doMd5(String secret) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("Cann't init MD5");
        }
        md.update(secret.getBytes());
        return HexConverter.bytesToHexString(md.digest()).toLowerCase();
    }

    private boolean isOpen(String hash, int index) {
        return (hash.charAt(index) >= 'b' && hash.charAt(index) <= 'f');
    }

}
