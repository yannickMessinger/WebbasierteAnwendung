package de.hsrm.mi.web.projekt.jwt;

/**
 * Record, das der JSON-Struktur der POST-Antwort zum Login
    entspricht
 * 
 * IJwtLoginResponseDTO {
    username: string,
    name: string,
    benutzerprofilid: number,
    jwtToken: string
}
 * 
 */
public record JwtLoginResponseDTO(String username, String name, Long benutzerprofilid, String jwtToken) {};
