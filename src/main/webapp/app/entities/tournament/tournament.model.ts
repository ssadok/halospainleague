import { BaseEntity } from './../../shared';

const enum Platform {
    'PC',
    'XB360',
    'XBONE',
    'PS3',
    'PS4'
}

const enum TournamentType {
    'ONE_VS_ONE',
    'TWO_VS_TWO',
    'THREE_VS_THREE',
    'FOUR_VS_FOUR',
    'FIVE_VS_FIVE',
    'SIX_VS_SIX'
}

export class Tournament implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public coverImageContentType?: string,
        public coverImage?: any,
        public platform?: Platform,
        public maxTeams?: number,
        public registrationStarts?: any,
        public registrationEnds?: any,
        public tournamentBegins?: any,
        public price?: number,
        public gamesPerRound?: number,
        public description?: any,
        public type?: TournamentType,
        public matches?: BaseEntity[],
        public teams?: BaseEntity[],
        public game?: BaseEntity,
    ) {
    }
}
