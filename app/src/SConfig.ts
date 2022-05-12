import { SSocketProps } from 'servisofts-socket'
import { SThemeThemes } from 'servisofts-component'
const SThemeProps: SThemeThemes = {
    default: {
        barStyle: "dark-content",
        barColor: "#ffffff",
        primary: "#ffffff",
        secondary: "#000000",
        background: "#cccccc",
        card: "#00000066",

    },
    dark: {
        barStyle: "light-content",
        barColor: "#000000",
        primary: "#000000",
        secondary: "#ffffff",
        background: "#222222",
        card: "#00000066",
    }
}

const SocketProps: SSocketProps = {
    name: 'multipagos',
    host: '192.168.3.2',
    port: {
        native: 10035,
        web: 20035,
        http: 30035,
    },
    ssl: false,
    cert: "MIIDxDCCAqygAwIBAgIEYl/jbjANBgkqhkiG9w0BAQsFADCBozELMAkGA1UEBhMCQk8xEjAQBgNVBAgMCUF2IEJhbnplcjETMBEGA1UEBwwKU2FudGEgQ3J1ejEXMBUGA1UECgwOU2Vydmlzb2Z0cyBTUkwxDDAKBgNVBAsMA3NxcjEbMBkGA1UEAwwSc3FyLnNlcnZpc29mdHMuY29tMScwJQYJKoZIhvcNAQkBFhhyaWNreS5wYXouZC45N0BnbWFpbC5jb20wHhcNMjIwNDIwMTA0MTUwWhcNMjIwNDIxMTA0MTUwWjCBozELMAkGA1UEBhMCQk8xEjAQBgNVBAgMCUF2IEJhbnplcjETMBEGA1UEBwwKU2FudGEgQ3J1ejEXMBUGA1UECgwOU2Vydmlzb2Z0cyBTUkwxDDAKBgNVBAsMA3NxcjEbMBkGA1UEAwwSc3FyLnNlcnZpc29mdHMuY29tMScwJQYJKoZIhvcNAQkBFhhyaWNreS5wYXouZC45N0BnbWFpbC5jb20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDFRA5rIBPpCK1Vtl8Gx5z4MvlJmRPy4s+af90FuxDn8hVARrVhr0sn8XDYl0Rutk6Zw/1hjHqr/3DSYzKuSjIJne6loHmOPPi6E76Z77u00cY6K6llquba2wk83c8Mu2X3dUCeryCgiKGXiprJU0EwGvXkCajiHe8pS+cSuOxDGNAL7FY7gKGHR5bp1ka3dyHTVDr27jEGktOlycmH/SCDvK3znSBKioUi0vmQ9eKRrhRzB2XBVIqAxVGO/1AwY43cqxeCyoZKjkkyRnzVTU6ugvotqUg4A247Q4taG1yC4SR8orVX7cYpKyBZpVj3Lwl731k+N+DT40vkgVaBbjEJAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAGsLwopcKVZHFf9Ip+IkmiuylqOE8zkUBdwUJMxPKstYw1uoz7PGe04dLUn6U7Qz+zj+wqQyYiU+ULzAfR0ylDJhV7m+Q/Rfn6TS2TYJl1T4apwE99fXg5L/VOhjeGnri0eEyTJ0Nv/4V2SWKCJg/dXqyh5gCoEJjG8BpnencRiuibZ8dm17hrcoQeE1pt325qVaFzhG1XjUfG6HfdUP4zt/YGLZLghs+lOjCZ2qStLPGe295fSHzImzC2ujBFMbh8+qtYzCD63uUDnuYp/T0h5D58r045e7kqocPt4ajXxgKKcP0XSAIsmxD89wxddcDNmt1vf4zeRoMhybSVAan54=",
    apis: {
        servicio: "http://servicio.ss.lo/http/"
        // rp: "http://192.168.0.21:30016/"
    }
}
export default {
    SocketProps,
    SThemeProps
}