# MusicService

REST service for searching information about music, albums. As a source of data last.fm API was used.
API has ability to provide information in XML and JSON formats. It's also possible to receive information in .docx file.

## Technology stack

Java 15, Spring Boot, Apache POI, JSON

## Endpoints

- /albumInfo
  Parameters:
  * track
  * singer
  * file - boolean, not required
  * format - xml or json(default), not required
