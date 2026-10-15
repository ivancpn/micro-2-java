package com.example.ordini;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Ordini in memoria: nessun database, i dati si azzerano a ogni riavvio.
 */
@ApplicationScoped
public class OrdiniService {

    private final Map<Long, Ordine> ordini = new ConcurrentHashMap<>();
    private final AtomicLong sequenza = new AtomicLong();

    public OrdiniService() {
        crea(new NuovoOrdine(1L, 2));
        crea(new NuovoOrdine(3L, 1));
    }

    public List<Ordine> tutti() {
        return ordini.values().stream()
                .sorted(Comparator.comparing(Ordine::id))
                .toList();
    }

    public Optional<Ordine> perId(Long id) {
        return Optional.ofNullable(ordini.get(id));
    }

    public Ordine crea(NuovoOrdine richiesta) {
        long id = sequenza.incrementAndGet();
        Ordine ordine = new Ordine(id, richiesta.prodottoId(), richiesta.quantita(), StatoOrdine.CREATO, Instant.now());
        ordini.put(id, ordine);
        return ordine;
    }

    public Optional<Ordine> annulla(Long id) {
        return Optional.ofNullable(ordini.computeIfPresent(id, (k, o) -> o.annullato()));
    }
}
