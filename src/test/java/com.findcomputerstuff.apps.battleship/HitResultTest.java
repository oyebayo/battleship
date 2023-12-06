package com.findcomputerstuff.apps.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class HitResultTest {
    @Test
    public void testHitResult() {
        HitResult hitResult = new HitResult(HitType.BLANK);
        assertEquals(HitType.BLANK, hitResult.getHitType());
        assertEquals(0, hitResult.getCellCount());

        hitResult = new HitResult(HitType.SHIP, 3);
        assertEquals(HitType.SHIP, hitResult.getHitType());
        assertEquals(3, hitResult.getCellCount());
    }
}
