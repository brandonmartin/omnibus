/**
 * This file has no copyright assigned and is placed in the Public Domain.
 * This file is part of the w64 mingw-runtime package.
 * No warranty is given; refer to the file DISCLAIMER.PD within this package.
 */
#ifndef __CRYPTUIAPI_H__
#define __CRYPTUIAPI_H__

#include <_mingw_unicode.h>
#include <wintrust.h>
#include <wincrypt.h>

#ifdef __cplusplus
extern "C" {
#endif

#include <pshpack8.h>

  WINBOOL WINAPI CryptUIDlgViewContext(DWORD dwContextType,const void *pvContext,HWND hwnd,LPCWSTR pwszTitle,DWORD dwFlags,void *pvReserved);
  PCCERT_CONTEXT WINAPI CryptUIDlgSelectCertificateFromStore(HCERTSTORE hCertStore,HWND hwnd,LPCWSTR pwszTitle,LPCWSTR pwszDisplayString,DWORD dwDontUseColumn,DWORD dwFlags,void *pvReserved);

#define CRYPTUI_SELECT_ISSUEDTO_COLUMN 0x000000001
#define CRYPTUI_SELECT_ISSUEDBY_COLUMN 0x000000002
#define CRYPTUI_SELECT_INTENDEDUSE_COLUMN 0x000000004
#define CRYPTUI_SELECT_FRIENDLYNAME_COLUMN 0x000000008
#define CRYPTUI_SELECT_LOCATION_COLUMN 0x000000010
#define CRYPTUI_SELECT_EXPIRATION_COLUMN 0x000000020

  typedef WINBOOL (WINAPI *PFNCFILTERPROC)(PCCERT_CONTEXT pCertContext,WINBOOL *pfInitialSelectedCert,void *pvCallbackData);

#define CRYPTUI_CERT_MGR_TAB_MASK 0x0000000F
#define CRYPTUI_CERT_MGR_PUBLISHER_TAB 0x00000004
#define CRYPTUI_CERT_MGR_SINGLE_TAB_FLAG 0x00008000

  typedef struct _CRYPTUI_CERT_MGR_STRUCT {
    DWORD dwSize;
    HWND hwndParent;
    DWORD dwFlags;
    LPCWSTR pwszTitle;
    LPCSTR pszInitUsageOID;
  } CRYPTUI_CERT_MGR_STRUCT,*PCRYPTUI_CERT_MGR_STRUCT;

  typedef const CRYPTUI_CERT_MGR_STRUCT *PCCRYPTUI_CERT_MGR_STRUCT;

  WINBOOL WINAPI CryptUIDlgCertMgr(PCCRYPTUI_CERT_MGR_STRUCT pCryptUICertMgr);

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_BLOB_INFO {
    DWORD dwSize;
    GUID *pGuidSubject;
    DWORD cbBlob;
    BYTE *pbBlob;
    LPCWSTR pwszDisplayName;
  } CRYPTUI_WIZ_DIGITAL_SIGN_BLOB_INFO,*PCRYPTUI_WIZ_DIGITAL_SIGN_BLOB_INFO;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_BLOB_INFO *PCCRYPTUI_WIZ_DIGITAL_SIGN_BLOB_INFO;

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_STORE_INFO {
    DWORD dwSize;
    DWORD cCertStore;
    HCERTSTORE *rghCertStore;
    PFNCFILTERPROC pFilterCallback;
    void *pvCallbackData;
  } CRYPTUI_WIZ_DIGITAL_SIGN_STORE_INFO,*PCRYPTUI_WIZ_DIGITAL_SIGN_STORE_INFO;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_STORE_INFO *PCCRYPTUI_WIZ_DIGITAL_SIGN_STORE_INFO;

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE_INFO {
    DWORD dwSize;
    LPWSTR pwszPvkFileName;
    LPWSTR pwszProvName;
    DWORD dwProvType;
  } CRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE_INFO,*PCRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE_INFO;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE_INFO *PCCRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE_INFO;

#define CRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE 0x01
#define CRYPTUI_WIZ_DIGITAL_SIGN_PVK_PROV 0x02

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_CERT_PVK_INFO {
    DWORD dwSize;
    LPWSTR pwszSigningCertFileName;
    DWORD dwPvkChoice;
    __C89_NAMELESS union {
      PCCRYPTUI_WIZ_DIGITAL_SIGN_PVK_FILE_INFO pPvkFileInfo;
      PCRYPT_KEY_PROV_INFO pPvkProvInfo;
    };
  } CRYPTUI_WIZ_DIGITAL_SIGN_CERT_PVK_INFO,*PCRYPTUI_WIZ_DIGITAL_SIGN_CERT_PVK_INFO;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_CERT_PVK_INFO *PCCRYPTUI_WIZ_DIGITAL_SIGN_CERT_PVK_INFO;
#define CRYPTUI_WIZ_DIGITAL_SIGN_COMMERCIAL 0x0001
#define CRYPTUI_WIZ_DIGITAL_SIGN_INDIVIDUAL 0x0002

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_EXTENDED_INFO {
    DWORD dwSize;
    DWORD dwAttrFlags;
    LPCWSTR pwszDescription;
    LPCWSTR pwszMoreInfoLocation;
    LPCSTR pszHashAlg;
    LPCWSTR pwszSigningCertDisplayString;
    HCERTSTORE hAdditionalCertStore;
    PCRYPT_ATTRIBUTES psAuthenticated;
    PCRYPT_ATTRIBUTES psUnauthenticated;
  } CRYPTUI_WIZ_DIGITAL_SIGN_EXTENDED_INFO,*PCRYPTUI_WIZ_DIGITAL_SIGN_EXTENDED_INFO;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_EXTENDED_INFO *PCCRYPTUI_WIZ_DIGITAL_SIGN_EXTENDED_INFO;

#define CRYPTUI_WIZ_DIGITAL_SIGN_SUBJECT_FILE 0x01
#define CRYPTUI_WIZ_DIGITAL_SIGN_SUBJECT_BLOB 0x02
#define CRYPTUI_WIZ_DIGITAL_SIGN_CERT 0x01
#define CRYPTUI_WIZ_DIGITAL_SIGN_STORE 0x02
#define CRYPTUI_WIZ_DIGITAL_SIGN_PVK 0x03

#define CRYPTUI_WIZ_DIGITAL_SIGN_ADD_CHAIN 0x00000001
#define CRYPTUI_WIZ_DIGITAL_SIGN_ADD_CHAIN_NO_ROOT 0x00000002

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_INFO {
    DWORD dwSize;
    DWORD dwSubjectChoice;
    __C89_NAMELESS union {
      LPCWSTR pwszFileName;
      PCCRYPTUI_WIZ_DIGITAL_SIGN_BLOB_INFO pSignBlobInfo;
    };
    DWORD dwSigningCertChoice;
    __C89_NAMELESS union {
      PCCERT_CONTEXT pSigningCertContext;
      PCCRYPTUI_WIZ_DIGITAL_SIGN_STORE_INFO pSigningCertStore;
      PCCRYPTUI_WIZ_DIGITAL_SIGN_CERT_PVK_INFO pSigningCertPvkInfo;
    };
    LPCWSTR pwszTimestampURL;
    DWORD dwAdditionalCertChoice;
    PCCRYPTUI_WIZ_DIGITAL_SIGN_EXTENDED_INFO pSignExtInfo;
  } CRYPTUI_WIZ_DIGITAL_SIGN_INFO,*PCRYPTUI_WIZ_DIGITAL_SIGN_INFO;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_INFO *PCCRYPTUI_WIZ_DIGITAL_SIGN_INFO;

  typedef struct _CRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT {
    DWORD dwSize;
    DWORD cbBlob;
    BYTE *pbBlob;
  } CRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT,*PCRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT;

  typedef const CRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT *PCCRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT;

#define CRYPTUI_WIZ_NO_UI 0x0001

  WINBOOL WINAPI CryptUIWizDigitalSign(DWORD dwFlags,HWND hwndParent,LPCWSTR pwszWizardTitle,PCCRYPTUI_WIZ_DIGITAL_SIGN_INFO pDigitalSignInfo,PCCRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT *ppSignContext);
  WINBOOL WINAPI CryptUIWizFreeDigitalSignContext(PCCRYPTUI_WIZ_DIGITAL_SIGN_CONTEXT pSignContext);

#define CRYPTUI_HIDE_HIERARCHYPAGE 0x00000001
#define CRYPTUI_HIDE_DETAILPAGE 0x00000002
#define CRYPTUI_DISABLE_EDITPROPERTIES 0x00000004
#define CRYPTUI_ENABLE_EDITPROPERTIES 0x00000008
#define CRYPTUI_DISABLE_ADDTOSTORE 0x00000010
#define CRYPTUI_ENABLE_ADDTOSTORE 0x00000020
#define CRYPTUI_ACCEPT_DECLINE_STYLE 0x00000040
#define CRYPTUI_IGNORE_UNTRUSTED_ROOT 0x00000080
#define CRYPTUI_DONT_OPEN_STORES 0x00000100
#define CRYPTUI_ONLY_OPEN_ROOT_STORE 0x00000200
#define CRYPTUI_WARN_UNTRUSTED_ROOT 0x00000400

#define CRYPTUI_ENABLE_REVOCATION_CHECKING 0x00000800

#define CRYPTUI_WARN_REMOTE_TRUST 0x00001000
#define CRYPTUI_DISABLE_EXPORT 0x00002000

#define CRYPTUI_ENABLE_REVOCATION_CHECK_END_CERT 0x00004000
#define CRYPTUI_ENABLE_REVOCATION_CHECK_CHAIN 0x00008000
#define CRYPTUI_ENABLE_REVOCATION_CHECK_CHAIN_EXCLUDE_ROOT CRYPTUI_ENABLE_REVOCATION_CHECKING

  typedef struct tagCRYPTUI_INITDIALOG_STRUCT {
    LPARAM lParam;
    PCCERT_CONTEXT pCertContext;
  } CRYPTUI_INITDIALOG_STRUCT,*PCRYPTUI_INITDIALOG_STRUCT;

  typedef struct tagCRYPTUI_VIEWCERTIFICATE_STRUCTW {
    DWORD dwSize;
    HWND hwndParent;
    DWORD dwFlags;
    LPCWSTR szTitle;
    PCCERT_CONTEXT pCertContext;
    LPCSTR *rgszPurposes;
    DWORD cPurposes;
    __C89_NAMELESS union {
      CRYPT_PROVIDER_DATA const *pCryptProviderData;
      HANDLE hWVTStateData;
    };
    WINBOOL fpCryptProviderDataTrustedUsage;
    DWORD idxSigner;
    DWORD idxCert;
    WINBOOL fCounterSigner;
    DWORD idxCounterSigner;
    DWORD cStores;
    HCERTSTORE *rghStores;
    DWORD cPropSheetPages;
    LPCPROPSHEETPAGEW rgPropSheetPages;
    DWORD nStartPage;
  } CRYPTUI_VIEWCERTIFICATE_STRUCTW,*PCRYPTUI_VIEWCERTIFICATE_STRUCTW;

  typedef const CRYPTUI_VIEWCERTIFICATE_STRUCTW *PCCRYPTUI_VIEWCERTIFICATE_STRUCTW;

  typedef struct tagCRYPTUI_VIEWCERTIFICATE_STRUCTA {
    DWORD dwSize;
    HWND hwndParent;
    DWORD dwFlags;
    LPCSTR szTitle;
    PCCERT_CONTEXT pCertContext;
    LPCSTR *rgszPurposes;
    DWORD cPurposes;
    __C89_NAMELESS union {
      CRYPT_PROVIDER_DATA const *pCryptProviderData;
      HANDLE hWVTStateData;
    };
    WINBOOL fpCryptProviderDataTrustedUsage;
    DWORD idxSigner;
    DWORD idxCert;
    WINBOOL fCounterSigner;
    DWORD idxCounterSigner;
    DWORD cStores;
    HCERTSTORE *rghStores;
    DWORD cPropSheetPages;
    LPCPROPSHEETPAGEA rgPropSheetPages;
    DWORD nStartPage;
  } CRYPTUI_VIEWCERTIFICATE_STRUCTA,*PCRYPTUI_VIEWCERTIFICATE_STRUCTA;

  typedef const CRYPTUI_VIEWCERTIFICATE_STRUCTA *PCCRYPTUI_VIEWCERTIFICATE_STRUCTA;

  WINBOOL WINAPI CryptUIDlgViewCertificateW(PCCRYPTUI_VIEWCERTIFICATE_STRUCTW pCertViewInfo,WINBOOL *pfPropertiesChanged);
  WINBOOL WINAPI CryptUIDlgViewCertificateA(PCCRYPTUI_VIEWCERTIFICATE_STRUCTA pCertViewInfo,WINBOOL *pfPropertiesChanged);

#define CryptUIDlgViewCertificate __MINGW_NAME_AW(CryptUIDlgViewCertificate)

#define PCRYPTUI_VIEWCERTIFICATE_STRUCT __MINGW_NAME_AW(PCRYPTUI_VIEWCERTIFICATE_STRUCT)
#define CRYPTUI_VIEWCERTIFICATE_STRUCT __MINGW_NAME_AW(CRYPTUI_VIEWCERTIFICATE_STRUCT)
#define PCCRYPTUI_VIEWCERTIFICATE_STRUCT __MINGW_NAME_AW(PCCRYPTUI_VIEWCERTIFICATE_STRUCT)

#define CRYPTUI_WIZ_EXPORT_CERT_CONTEXT 1
#define CRYPTUI_WIZ_EXPORT_CTL_CONTEXT 2
#define CRYPTUI_WIZ_EXPORT_CRL_CONTEXT 3
#define CRYPTUI_WIZ_EXPORT_CERT_STORE 4
#define CRYPTUI_WIZ_EXPORT_CERT_STORE_CERTIFICATES_ONLY 5

  typedef struct _CRYPTUI_WIZ_EXPORT_INFO {
    DWORD dwSize;
    LPCWSTR pwszExportFileName;
    DWORD dwSubjectChoice;
    __C89_NAMELESS union {
      PCCERT_CONTEXT pCertContext;
      PCCTL_CONTEXT pCTLContext;
      PCCRL_CONTEXT pCRLContext;
      HCERTSTORE hCertStore;
    };
    DWORD cStores;
    HCERTSTORE *rghStores;
  } CRYPTUI_WIZ_EXPORT_INFO,*PCRYPTUI_WIZ_EXPORT_INFO;

  typedef const CRYPTUI_WIZ_EXPORT_INFO *PCCRYPTUI_WIZ_EXPORT_INFO;

#define CRYPTUI_WIZ_EXPORT_FORMAT_DER 1
#define CRYPTUI_WIZ_EXPORT_FORMAT_PFX 2
#define CRYPTUI_WIZ_EXPORT_FORMAT_PKCS7 3
#define CRYPTUI_WIZ_EXPORT_FORMAT_BASE64 4
#define CRYPTUI_WIZ_EXPORT_FORMAT_SERIALIZED_CERT_STORE 5

  typedef struct _CRYPTUI_WIZ_EXPORT_CERTCONTEXT_INFO {
    DWORD dwSize;
    DWORD dwExportFormat;
    WINBOOL fExportChain;
    WINBOOL fExportPrivateKeys;
    LPCWSTR pwszPassword;
    WINBOOL fStrongEncryption;
  } CRYPTUI_WIZ_EXPORT_CERTCONTEXT_INFO,*PCRYPTUI_WIZ_EXPORT_CERTCONTEXT_INFO;

  typedef const CRYPTUI_WIZ_EXPORT_CERTCONTEXT_INFO *PCCRYPTUI_WIZ_EXPORT_CERTCONTEXT_INFO;

  WINBOOL WINAPI CryptUIWizExport(DWORD dwFlags,HWND hwndParent,LPCWSTR pwszWizardTitle,PCCRYPTUI_WIZ_EXPORT_INFO pExportInfo,void *pvoid);

#define CRYPTUI_WIZ_IMPORT_SUBJECT_FILE 1
#define CRYPTUI_WIZ_IMPORT_SUBJECT_CERT_CONTEXT 2
#define CRYPTUI_WIZ_IMPORT_SUBJECT_CTL_CONTEXT 3
#define CRYPTUI_WIZ_IMPORT_SUBJECT_CRL_CONTEXT 4
#define CRYPTUI_WIZ_IMPORT_SUBJECT_CERT_STORE 5

  typedef struct _CRYPTUI_WIZ_IMPORT_SUBJECT_INFO {
    DWORD dwSize;
    DWORD dwSubjectChoice;
    __C89_NAMELESS union {
      LPCWSTR pwszFileName;
      PCCERT_CONTEXT pCertContext;
      PCCTL_CONTEXT pCTLContext;
      PCCRL_CONTEXT pCRLContext;
      HCERTSTORE hCertStore;
    };
    DWORD dwFlags;
    LPCWSTR pwszPassword;
  } CRYPTUI_WIZ_IMPORT_SRC_INFO,*PCRYPTUI_WIZ_IMPORT_SRC_INFO;

  typedef const CRYPTUI_WIZ_IMPORT_SRC_INFO *PCCRYPTUI_WIZ_IMPORT_SRC_INFO;

#define CRYPTUI_WIZ_IMPORT_NO_CHANGE_DEST_STORE 0x00010000
#define CRYPTUI_WIZ_IMPORT_ALLOW_CERT 0x00020000
#define CRYPTUI_WIZ_IMPORT_ALLOW_CRL 0x00040000
#define CRYPTUI_WIZ_IMPORT_ALLOW_CTL 0x00080000
#define CRYPTUI_WIZ_IMPORT_TO_LOCALMACHINE 0x00100000
#define CRYPTUI_WIZ_IMPORT_TO_CURRENTUSER 0x00200000
#define CRYPTUI_WIZ_IMPORT_REMOTE_DEST_STORE 0x00400000

  WINBOOL WINAPI CryptUIWizImport(DWORD dwFlags,HWND hwndParent,LPCWSTR pwszWizardTitle,PCCRYPTUI_WIZ_IMPORT_SRC_INFO pImportSrc,HCERTSTORE hDestCertStore);

#include <poppack.h>

#ifdef __cplusplus
}
#endif
#endif
