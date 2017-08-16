import { BaseEntity } from './../../shared';

const enum Platform {
    'PC',
    'XB360',
    'XBONE',
    'PS3',
    'PS4'
}

export class Game implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public year?: any,
        public photoContentType?: string,
        public photo?: any,
        public rate?: number,
        public platform?: Platform,
        public maps?: BaseEntity[],
        public tournaments?: BaseEntity[],
    ) {
    }
}
