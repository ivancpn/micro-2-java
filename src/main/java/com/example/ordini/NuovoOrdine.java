package com.example.ordini;

public record NuovoOrdine(Long prodottoId, Integer quantita) {

    public boolean valido() {
        return prodottoId != null && prodottoId > 0 && quantita != null && quantita > 0;
    }
}
