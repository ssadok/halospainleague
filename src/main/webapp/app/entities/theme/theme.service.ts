import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Theme } from './theme.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ThemeService {

    private resourceUrl = 'api/themes';

    constructor(private http: Http) { }

    create(theme: Theme): Observable<Theme> {
        const copy = this.convert(theme);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(theme: Theme): Observable<Theme> {
        const copy = this.convert(theme);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Theme> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(theme: Theme): Theme {
        const copy: Theme = Object.assign({}, theme);
        return copy;
    }
}
