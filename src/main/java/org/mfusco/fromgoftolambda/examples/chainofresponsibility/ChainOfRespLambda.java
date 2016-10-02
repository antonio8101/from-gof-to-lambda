package org.mfusco.fromgoftolambda.examples.chainofresponsibility;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class ChainOfRespLambda {

    private static Optional<String> parseText(File file) {
        return file.getType() == File.Type.TEXT ?
               Optional.of("Text file: " + file.getContent()) :
               Optional.empty();
    }

    private static Optional<String> parsePresentation(File file) {
        return file.getType() == File.Type.PRESENTATION ?
               Optional.of("Presentation file: " + file.getContent()) :
               Optional.empty();
    }

    private static Optional<String> parseAudio(File file) {
        return file.getType() == File.Type.AUDIO ?
               Optional.of("Audio file: " + file.getContent()) :
               Optional.empty();
    }

    private static Optional<String> parseVideo(File file) {
        return file.getType() == File.Type.VIDEO ?
               Optional.of("Video file: " + file.getContent()) :
               Optional.empty();
    }

    public static void main( String[] args ) {

        File file = new File( File.Type.TEXT, "Calcolodellespese" );

        Stream<Function<File,Optional<String>>> parsers = Stream.of(ChainOfRespLambda::parseText,
                                                                    ChainOfRespLambda::parseAudio); // Stream di metodi
        Stream<Optional<String>> risultati; // # Lambda.. map per ogni item presente esegue ciascuno dei metodi dello stream
        risultati = parsers.map(f -> f.apply(file));

        // Map restituisce uno stream di Velori risultanti dall'esecuzione dei metodi passati sul parametro file

        // Comincia Analisi dei risultati

        Stream<Optional<String>> risultatiValidi;
        // filter restituisce uno Stream di risultati corrispondenti / elimina i risultati non validi
        risultatiValidi = risultati.filter(Optional::isPresent);

        Optional<Optional<String>> risultatoSingolo;
        // Ritorna un Optional del tipo contenuto nello STREAM
        risultatoSingolo = risultatiValidi.findFirst();

        Optional<String> risultato;
        // Estrae il risultato mappandolo in corrispondenza all'item di partenza
        risultato = risultatoSingolo.flatMap(Function.identity());

        String risultatoText;
        risultatoText = risultato.orElse("n.p.");

        System.out.println(risultato);
        System.out.println(risultatoText);
    }
}
