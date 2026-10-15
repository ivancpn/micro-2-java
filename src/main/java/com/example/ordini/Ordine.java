package com.example.ordini;

import java.time.Instant;

public record Ordine(Long id, Long prodottoId, int quantita, StatoOrdine stato, Instant creatoIl) {

    public Ordine annullato() {
        return new Ordine(id, prodottoId, quantita, StatoOrdine.ANNULLATO, creatoIl);
    }
}
