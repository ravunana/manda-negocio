import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { ContactoEmpresaDetailComponent } from 'app/entities/contacto-empresa/contacto-empresa-detail.component';
import { ContactoEmpresa } from 'app/shared/model/contacto-empresa.model';

describe('Component Tests', () => {
  describe('ContactoEmpresa Management Detail Component', () => {
    let comp: ContactoEmpresaDetailComponent;
    let fixture: ComponentFixture<ContactoEmpresaDetailComponent>;
    const route = ({ data: of({ contactoEmpresa: new ContactoEmpresa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [ContactoEmpresaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ContactoEmpresaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContactoEmpresaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contactoEmpresa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
