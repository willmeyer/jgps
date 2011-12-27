package com.willmeyer.jgps.nmea;

public interface ParsedSentence
{

    public abstract String inPlainEnglish();

    public abstract String getSentenceType();

    public abstract int getSentenceID();
}
