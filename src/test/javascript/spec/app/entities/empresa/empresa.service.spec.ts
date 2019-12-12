import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { EmpresaService } from 'app/entities/empresa/empresa.service';
import { IEmpresa, Empresa } from 'app/shared/model/empresa.model';

describe('Service Tests', () => {
  describe('Empresa Service', () => {
    let injector: TestBed;
    let service: EmpresaService;
    let httpMock: HttpTestingController;
    let elemDefault: IEmpresa;
    let expectedResult;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(EmpresaService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Empresa(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'image/png',
        'AAAAAAA',
        0,
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        currentDate,
        'AAAAAAA',
        false
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fundacao: currentDate.format(DATE_FORMAT),
            aberturaExercio: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Empresa', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fundacao: currentDate.format(DATE_FORMAT),
            aberturaExercio: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fundacao: currentDate,
            aberturaExercio: currentDate
          },
          returnedFromService
        );
        service
          .create(new Empresa(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Empresa', () => {
        const returnedFromService = Object.assign(
          {
            tipoSociedade: 'BBBBBB',
            nome: 'BBBBBB',
            logotipo: 'BBBBBB',
            capitalSocial: 1,
            fundacao: currentDate.format(DATE_FORMAT),
            nif: 'BBBBBB',
            numeroRegistroComercial: 'BBBBBB',
            despesaFixa: 1,
            despesaVariavel: 1,
            aberturaExercio: currentDate.format(DATE_FORMAT),
            designacaoComercial: 'BBBBBB',
            sede: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fundacao: currentDate,
            aberturaExercio: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Empresa', () => {
        const returnedFromService = Object.assign(
          {
            tipoSociedade: 'BBBBBB',
            nome: 'BBBBBB',
            logotipo: 'BBBBBB',
            capitalSocial: 1,
            fundacao: currentDate.format(DATE_FORMAT),
            nif: 'BBBBBB',
            numeroRegistroComercial: 'BBBBBB',
            despesaFixa: 1,
            despesaVariavel: 1,
            aberturaExercio: currentDate.format(DATE_FORMAT),
            designacaoComercial: 'BBBBBB',
            sede: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            fundacao: currentDate,
            aberturaExercio: currentDate
          },
          returnedFromService
        );
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Empresa', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
