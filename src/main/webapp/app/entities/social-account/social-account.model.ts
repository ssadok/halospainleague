import { BaseEntity } from './../../shared';

const enum Platform {
    'PC',
    'XB360',
    'XBONE',
    'PS3',
    'PS4'
}

export class SocialAccount implements BaseEntity {
    constructor(
        public id?: number,
        public nick?: string,
        public platform?: Platform,
        public token?: string,
        public playerId?: number,
    ) {
    }
}
